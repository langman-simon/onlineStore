package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.cart.CartItem;
import com.hyperion.dataAccess.dao.OrderDataAccess;
import com.hyperion.dataAccess.dao.ProductDataAccess;
import com.hyperion.model.Order;
import com.hyperion.model.OrderItem;
import com.hyperion.model.Product;
import com.hyperion.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final ProductDataAccess productDataAccess;
    private final OrderDataAccess orderDataAccess;
    private final PromotionService promotionService;

    public OrderService(
            ProductDataAccess productDataAccess,
            OrderDataAccess orderDataAccess,
            PromotionService promotionService
    ) {
        this.productDataAccess = productDataAccess;
        this.orderDataAccess = orderDataAccess;
        this.promotionService = promotionService;
    }

    @Transactional
    public Order validateOrder(
            Cart cart,
            User user,
            boolean authenticated
    ) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException(
                    "error.cart.empty"
            );
        }

        if (user == null) {
            throw new IllegalArgumentException(
                    "error.order.userRequired"
            );
        }

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal originalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            Long productId = cartItem.getProduct().getId();

            Product product = productDataAccess.findById(productId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "error.product.notFound"
                            )
                    );

            int quantity = cartItem.getQuantity();

            if (quantity <= 0) {
                throw new IllegalArgumentException(
                        "error.quantity.invalid"
                );
            }

            if (product.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "error.stock.insufficient"
                );
            }

            OrderItem orderItem = new OrderItem(
                    product,
                    quantity
            );

            orderItems.add(orderItem);
            originalPrice = originalPrice.add(
                    orderItem.getSubtotal()
            );
        }

        BigDecimal discountAmount =
                promotionService.calculateDiscount(
                        cart.getItems(),
                        authenticated
                );

        BigDecimal finalPrice =
                promotionService.calculateFinalPrice(
                        cart.getItems(),
                        authenticated
                );

        Order order = new Order();
        order.setOriginalPrice(originalPrice);
        order.setDiscountAmount(discountAmount);
        order.setTotalPrice(finalPrice);
        order.setItems(orderItems);

        Order savedOrder = orderDataAccess.create(order, user.getId());

        cart.clear();
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<Order> findOrdersForUser(
            String userLogin
    ) {
        return orderDataAccess.findByUserLogin(userLogin);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderForUser(
            Long orderId,
            String userLogin
    ) {
        return orderDataAccess.findByIdAndUserLogin(
                orderId,
                userLogin
        );
    }

    @Transactional
    public Order validatePayment(
            Long orderId,
            BigDecimal paidAmount,
            String paymentReference
    ) {
        if (paidAmount == null) {
            throw new IllegalArgumentException(
                    "error.payment.amountMissing"
            );
        }

        if (paymentReference == null
                || paymentReference.isBlank()) {
            throw new IllegalArgumentException(
                    "error.payment.referenceMissing"
            );
        }

        String normalizedReference = paymentReference.trim();

        Order order =
                orderDataAccess.findById(orderId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "error.order.notFound"
                                )
                        );

        if ("PAID".equals(order.getStatus())) {
            if (normalizedReference.equals(
                    order.getPaymentReference()
            )) {
                return order;
            }

            throw new IllegalArgumentException(
                    "error.order.alreadyPaid"
            );
        }

        if (orderDataAccess
                .existsByPaymentReference(normalizedReference)) {
            throw new IllegalArgumentException(
                    "error.payment.duplicateReference"
            );
        }

        if (order.getTotalPrice().compareTo(paidAmount) != 0) {
            throw new IllegalArgumentException(
                    "error.payment.amountMismatch"
            );
        }

        for (OrderItem orderItem : order.getItems()) {
            Product product = productDataAccess.findById(
                            orderItem.getProduct().getId()
                    )
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "error.product.notFound"
                            )
                    );

            int quantity = orderItem.getQuantity();

            if (product.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "error.stock.insufficient"
                );
            }

            productDataAccess.updateStock(
                    product.getId(),
                    product.getStock() - quantity
            );
        }

        orderDataAccess.markAsPaid(orderId, normalizedReference);

        order.setPaymentReference(normalizedReference);
        order.setStatus("PAID");

        return order;
    }

    @Transactional
    public void cancelOrder(
            Long orderId,
            String userLogin
    ) {
        Order order =
                orderDataAccess.findByIdAndUserLogin(
                                orderId,
                                userLogin
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "error.order.notFound"
                                )
                        );

        if ("PAID".equals(order.getStatus())) {
            throw new IllegalArgumentException(
                    "error.order.paidCannotCancel"
            );
        }

        orderDataAccess.delete(order.getId());
    }
}
