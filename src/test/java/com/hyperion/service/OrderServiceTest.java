package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.dataAccess.entity.CustomerOrderEntity;
import com.hyperion.dataAccess.entity.UserEntity;
import com.hyperion.dataAccess.entity.ProductEntity;
import com.hyperion.dataAccess.repository.CustomerOrderRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import com.hyperion.model.Product;
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

    private ProductRepository productRepository;
    private CustomerOrderRepository customerOrderRepository;
    private PromotionService promotionService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        customerOrderRepository = mock(CustomerOrderRepository.class);
        promotionService = mock(PromotionService.class);
        orderService = new OrderService(
                productRepository,
                customerOrderRepository,
                promotionService
        );
    }

    @Test
    void shouldRecalculateOrderFromDatabasePrices() {
        Product cartProduct = mock(Product.class);
        when(cartProduct.getId()).thenReturn(10L);
        when(cartProduct.getPrice())
                .thenReturn(new BigDecimal("5.00"));
        when(cartProduct.getStock()).thenReturn(10);

        ProductEntity databaseProduct = mock(ProductEntity.class);
        when(databaseProduct.getId()).thenReturn(10L);
        when(databaseProduct.getPrice())
                .thenReturn(new BigDecimal("20.00"));
        when(databaseProduct.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addProduct(cartProduct, 2);

        when(productRepository.findById(10L))
                .thenReturn(Optional.of(databaseProduct));
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
        CustomerOrderEntity order = new CustomerOrderEntity(
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