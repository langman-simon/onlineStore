package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.dataAccess.entity.CustomerOrderEntity;
import com.hyperion.dataAccess.entity.UserEntity;
import com.hyperion.dataAccess.entity.ProductEntity;
import com.hyperion.dataAccess.repository.CustomerOrderRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;

class OrderServiceTest {

    private ProductRepository weaponRepository;
    private CustomerOrderRepository customerOrderRepository;
    private PromotionService promotionService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        weaponRepository = mock(ProductRepository.class);
        customerOrderRepository = mock(CustomerOrderRepository.class);
        promotionService = mock(PromotionService.class);
        orderService = new OrderService(
                weaponRepository,
                customerOrderRepository,
                promotionService
        );
    }

    @Test
    void shouldRecalculateOrderFromDatabasePrices() {
        ProductEntity cartWeapon = mock(ProductEntity.class);
        when(cartWeapon.getId()).thenReturn(10L);
        when(cartWeapon.getPrice())
                .thenReturn(new BigDecimal("5.00"));
        when(cartWeapon.getStock()).thenReturn(10);

        ProductEntity databaseWeapon = mock(ProductEntity.class);
        when(databaseWeapon.getId()).thenReturn(10L);
        when(databaseWeapon.getPrice())
                .thenReturn(new BigDecimal("20.00"));
        when(databaseWeapon.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addWeapon(cartWeapon, 2);

        when(weaponRepository.findById(10L))
                .thenReturn(Optional.of(databaseWeapon));
        when(promotionService.calculateDiscount(
                anyList(),
                eq(true)
        )).thenReturn(BigDecimal.ZERO);
        when(promotionService.calculateFinalPrice(
                anyList(),
                eq(true)
        )).thenReturn(new BigDecimal("40.00"));
        when(customerOrderRepository.save(any(CustomerOrderEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CustomerOrderEntity order = orderService.validateOrder(
                cart,
                new UserEntity(),
                true
        );

        assertThat(order.getOriginalPrice())
                .isEqualByComparingTo("40.00");
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getItems().getFirst().getUnitPrice())
                .isEqualByComparingTo("20.00");
        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void shouldRejectPaymentWithWrongAmount() {
        CustomerOrderEntity order = new CustomerOrderEntity(
                new BigDecimal("100.00"),
                BigDecimal.ZERO,
                new BigDecimal("100.00")
        );

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        assertThatThrownBy(() ->
                orderService.validatePayment(
                        1L,
                        new BigDecimal("99.99"),
                        "PAYPAL-123"
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.payment.amountMismatch");
    }

    @Test
    void shouldStorePaymentReferenceAfterValidatedPayment() {
        CustomerOrderEntity order = new CustomerOrderEntity(
                new BigDecimal("100.00"),
                BigDecimal.ZERO,
                new BigDecimal("100.00")
        );

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(order));
        when(customerOrderRepository.existsByPaymentReference(
                "PAYPAL-123"
        )).thenReturn(false);
        when(customerOrderRepository.save(order))
                .thenReturn(order);

        CustomerOrderEntity paidOrder = orderService.validatePayment(
                1L,
                new BigDecimal("100.00"),
                "PAYPAL-123"
        );

        assertThat(paidOrder.getStatus()).isEqualTo("PAID");
        assertThat(paidOrder.getPaymentReference())
                .isEqualTo("PAYPAL-123");
        verify(customerOrderRepository).save(order);
    }

    @Test
    void shouldRejectAlreadyUsedPaymentReference() {
        CustomerOrder order = new CustomerOrder(
                new BigDecimal("100.00"),
                BigDecimal.ZERO,
                new BigDecimal("100.00")
        );

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(customerOrderRepository.existsByPaymentReference(
                "PAYPAL-123"
        )).thenReturn(true);

        assertThatThrownBy(() ->
                orderService.validatePayment(
                        1L,
                        new BigDecimal("100.00"),
                        "PAYPAL-123"
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.payment.duplicateReference");
    }
}
