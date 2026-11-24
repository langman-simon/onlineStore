package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.cart.CartItem;
import com.hyperion.dataAccess.entity.CustomerOrderEntity;
import com.hyperion.dataAccess.entity.OrderItemEntity;
import com.hyperion.dataAccess.entity.ProductEntity;
import com.hyperion.dataAccess.repository.CustomerOrderRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import com.hyperion.dataAccess.repository.UserRepository;
import com.hyperion.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final PromotionService promotionService;
    private final UserRepository userRepository;

    public OrderService(
            ProductRepository productRepository,
            CustomerOrderRepository customerOrderRepository,
            PromotionService promotionService,
            UserRepository userRepository
    ) {
        this.productRepository = productRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.promotionService = promotionService;
        this.userRepository = userRepository;
    }

    @Transactional
    public CustomerOrderEntity validateOrder(
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

        List<OrderItemEntity> orderItems = new ArrayList<>();
        BigDecimal originalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            Long productId = cartItem.getProduct().getId();

            ProductEntity product = productRepository.findById(productId)
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

            OrderItemEntity orderItem = new OrderItemEntity(
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

        CustomerOrderEntity order = new CustomerOrderEntity(
                originalPrice,
                discountAmount,
                finalPrice
        );
        order.setUser(userRepository.getReferenceById(user.getId()));
        orderItems.forEach(order::addItem);

        CustomerOrderEntity savedOrder =
                customerOrderRepository.save(order);

        cart.clear();
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<CustomerOrderEntity> findOrdersForUser(
            String userLogin
    ) {
        return customerOrderRepository
                .findByUserLoginOrderByCreatedAtDesc(userLogin);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerOrderEntity> findOrderForUser(
            Long orderId,
            String userLogin
    ) {
        return customerOrderRepository.findByIdAndUserLogin(
                orderId,
                userLogin
        );
    }

    @Transactional
    public CustomerOrderEntity validatePayment(
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

        CustomerOrderEntity order =
                customerOrderRepository.findById(orderId)
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

        if (customerOrderRepository
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

        for (OrderItemEntity orderItem : order.getItems()) {
            ProductEntity product = productRepository.findById(
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

            product.setStock(
                    product.getStock() - quantity
            );
        }

        order.setPaymentReference(normalizedReference);
        order.setStatus("PAID");

        return customerOrderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(
            Long orderId,
            String userLogin
    ) {
        CustomerOrderEntity order =
                customerOrderRepository.findByIdAndUserLogin(
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

        customerOrderRepository.delete(order);
    }
}
