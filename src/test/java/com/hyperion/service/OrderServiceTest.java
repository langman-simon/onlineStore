package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.dataAccess.dao.OrderDataAccess;
import com.hyperion.dataAccess.dao.ProductDataAccess;
import com.hyperion.model.Order;
import com.hyperion.model.Product;
import com.hyperion.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    private ProductDataAccess productDataAccess;
    private OrderDataAccess orderDataAccess;
    private PromotionService promotionService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        productDataAccess = mock(ProductDataAccess.class);
        orderDataAccess = mock(OrderDataAccess.class);
        promotionService = mock(PromotionService.class);
        orderService = new OrderService(
                productDataAccess,
                orderDataAccess,
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

        Product databaseProduct = mock(Product.class);
        when(databaseProduct.getId()).thenReturn(10L);
        when(databaseProduct.getPrice())
                .thenReturn(new BigDecimal("20.00"));
        when(databaseProduct.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addProduct(cartProduct, 2);

        when(productDataAccess.findById(10L))
                .thenReturn(Optional.of(databaseProduct));
        when(promotionService.calculateDiscount(
                anyList(),
                eq(true)
        )).thenReturn(BigDecimal.ZERO);
        when(promotionService.calculateFinalPrice(
                anyList(),
                eq(true)
        )).thenReturn(new BigDecimal("40.00"));
        when(orderDataAccess.create(any(Order.class), any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.validateOrder(
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
        Order order = pendingOrder("100.00");

        when(orderDataAccess.findById(1L))
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
        Order order = pendingOrder("100.00");

        when(orderDataAccess.findById(1L))
                .thenReturn(Optional.of(order));
        when(orderDataAccess.existsByPaymentReference(
                "PAYPAL-123"
        )).thenReturn(false);

        Order paidOrder = orderService.validatePayment(
                1L,
                new BigDecimal("100.00"),
                "PAYPAL-123"
        );

        assertThat(paidOrder.getStatus()).isEqualTo("PAID");
        assertThat(paidOrder.getPaymentReference())
                .isEqualTo("PAYPAL-123");
        verify(orderDataAccess).markAsPaid(1L, "PAYPAL-123");
    }

    @Test
    void shouldRejectAlreadyUsedPaymentReference() {
        Order order = pendingOrder("100.00");

        when(orderDataAccess.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderDataAccess.existsByPaymentReference(
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

    private Order pendingOrder(String total) {
        Order order = new Order();
        order.setId(1L);
        order.setOriginalPrice(new BigDecimal(total));
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setTotalPrice(new BigDecimal(total));
        order.setStatus("PENDING_PAYMENT");
        return order;
    }
}
