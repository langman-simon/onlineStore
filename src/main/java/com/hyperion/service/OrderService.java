package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.cart.CartItem;
import com.hyperion.model.CustomerOrder;
import com.hyperion.model.OrderItem;
import com.hyperion.model.User;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.repository.WeaponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final WeaponRepository weaponRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final PromotionService promotionService;

    public OrderService(
            WeaponRepository weaponRepository,
            CustomerOrderRepository customerOrderRepository,
            PromotionService promotionService
    ) {
        this.weaponRepository = weaponRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.promotionService = promotionService;
    }

    @Transactional
    public CustomerOrder validateOrder(
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
            Long weaponId = cartItem.getWeapon().getId();

            Weapon weapon = weaponRepository.findById(weaponId)
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

            if (weapon.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "error.stock.insufficient"
                );
            }

            OrderItem orderItem = new OrderItem(
                    weapon,
                    quantity
            );

            orderItems.add(orderItem);
            originalPrice = originalPrice.add(
                    orderItem.getSubtotal()
            );
        }

        BigDecimal discountAmount =
                promotionService.calculateDiscount(
                        originalPrice,
                        authenticated
                );

        BigDecimal finalPrice =
                promotionService.calculateFinalPrice(
                        originalPrice,
                        authenticated
                );

        CustomerOrder order = new CustomerOrder(
                originalPrice,
                discountAmount,
                finalPrice
        );
        order.setUser(user);
        orderItems.forEach(order::addItem);

        CustomerOrder savedOrder =
                customerOrderRepository.save(order);

        cart.clear();
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<CustomerOrder> findOrdersForUser(
            String userLogin
    ) {
        return customerOrderRepository
                .findByUserLoginOrderByCreatedAtDesc(userLogin);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerOrder> findOrderForUser(
            Long orderId,
            String userLogin
    ) {
        return customerOrderRepository.findByIdAndUserLogin(
                orderId,
                userLogin
        );
    }

    @Transactional
    public CustomerOrder validatePayment(
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

        CustomerOrder order =
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

        for (OrderItem orderItem : order.getItems()) {
            Weapon weapon = weaponRepository.findById(
                            orderItem.getWeapon().getId()
                    )
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "error.product.notFound"
                            )
                    );

            int quantity = orderItem.getQuantity();

            if (weapon.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "error.stock.insufficient"
                );
            }

            weapon.setStock(
                    weapon.getStock() - quantity
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
        CustomerOrder order =
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
