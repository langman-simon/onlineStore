package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.model.CustomerOrder;
import com.hyperion.model.User;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.repository.WeaponRepository;
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

class OrderServiceTest {

    private WeaponRepository weaponRepository;
    private CustomerOrderRepository customerOrderRepository;
    private PromotionService promotionService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        weaponRepository = mock(WeaponRepository.class);
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
        Weapon cartWeapon = mock(Weapon.class);
        when(cartWeapon.getId()).thenReturn(10L);
        when(cartWeapon.getPrice())
                .thenReturn(new BigDecimal("5.00"));
        when(cartWeapon.getStock()).thenReturn(10);

        Weapon databaseWeapon = mock(Weapon.class);
        when(databaseWeapon.getId()).thenReturn(10L);
        when(databaseWeapon.getPrice())
                .thenReturn(new BigDecimal("20.00"));
        when(databaseWeapon.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addWeapon(cartWeapon, 2);

        when(weaponRepository.findById(10L))
                .thenReturn(Optional.of(databaseWeapon));
        when(promotionService.calculateDiscount(
                new BigDecimal("40.00"),
                true
        )).thenReturn(BigDecimal.ZERO);
        when(promotionService.calculateFinalPrice(
                new BigDecimal("40.00"),
                true
        )).thenReturn(new BigDecimal("40.00"));
        when(customerOrderRepository.save(any(CustomerOrder.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CustomerOrder order = orderService.validateOrder(
                cart,
                new User(),
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
        CustomerOrder order = new CustomerOrder(
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
        CustomerOrder order = new CustomerOrder(
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

        CustomerOrder paidOrder = orderService.validatePayment(
                1L,
                new BigDecimal("100.00"),
                "PAYPAL-123"
        );

        assertThat(paidOrder.getStatus()).isEqualTo("PAID");
        assertThat(paidOrder.getPaymentReference())
                .isEqualTo("PAYPAL-123");
        verify(customerOrderRepository).save(order);
    }
}
