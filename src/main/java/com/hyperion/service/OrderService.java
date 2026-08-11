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

        BigDecimal originalPrice =
                cart.getTotalPrice();

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

        CustomerOrder order =
                new CustomerOrder(
                        originalPrice,
                        discountAmount,
                        finalPrice
                );

        order.setUser(user);

        for (CartItem cartItem : cart.getItems()) {

            Long weaponId =
                    cartItem.getWeapon().getId();

            Weapon weapon =
                    weaponRepository.findById(weaponId)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "error.product.notFound"
                                    )
                            );

            int quantity =
                    cartItem.getQuantity();

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

            OrderItem orderItem =
                    new OrderItem(
                            weapon,
                            quantity
                    );

            order.addItem(orderItem);
        }

        CustomerOrder savedOrder =
                customerOrderRepository.save(order);

        cart.clear();

        return savedOrder;
    }

    @Transactional
    public CustomerOrder validatePayment(
            Long orderId
    ) {
        CustomerOrder order =
                customerOrderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "error.order.notFound"
                                )
                        );

        if ("PAID".equals(order.getStatus())) {
            throw new IllegalArgumentException(
                    "error.order.alreadyPaid"
            );
        }

        for (OrderItem orderItem : order.getItems()) {

            Weapon weapon =
                    weaponRepository.findById(
                                    orderItem.getWeapon().getId()
                            )
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "error.product.notFound"
                                    )
                            );

            int quantity =
                    orderItem.getQuantity();

            if (weapon.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "error.stock.insufficient"
                );
            }

            weapon.setStock(
                    weapon.getStock() - quantity
            );
        }

        order.setStatus("PAID");

        return customerOrderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(
            Long orderId,
            String userLogin
    ) {
        CustomerOrder order =
                customerOrderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "error.order.notFound"
                                )
                        );

        if (order.getUser() == null
                || !order.getUser()
                .getLogin()
                .equals(userLogin)) {

            throw new IllegalArgumentException(
                    "error.order.cancelForbidden"
            );
        }

        if ("PAID".equals(order.getStatus())) {
            throw new IllegalArgumentException(
                    "error.order.paidCannotCancel"
            );
        }

        customerOrderRepository.delete(order);
    }
}