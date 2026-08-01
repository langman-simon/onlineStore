package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.cart.CartItem;
import com.hyperion.model.CustomerOrder;
import com.hyperion.model.OrderItem;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.repository.WeaponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final WeaponRepository weaponRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public OrderService(
            WeaponRepository weaponRepository,
            CustomerOrderRepository customerOrderRepository
    ) {
        this.weaponRepository = weaponRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    @Transactional
    public CustomerOrder validateOrder(Cart cart) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Le panier est vide.");
        }

        CustomerOrder order = new CustomerOrder(cart.getTotalPrice());

        for (CartItem cartItem : cart.getItems()) {
            Long weaponId = cartItem.getWeapon().getId();

            Weapon weapon = weaponRepository.findById(weaponId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Produit introuvable : " + weaponId
                            )
                    );

            int quantity = cartItem.getQuantity();

            if (quantity <= 0) {
                throw new IllegalArgumentException(
                        "Quantité invalide pour le produit : "
                                + weapon.getName()
                );
            }

            if (weapon.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "Stock insuffisant pour le produit : "
                                + weapon.getName()
                );
            }

            OrderItem orderItem = new OrderItem(weapon, quantity);
            order.addItem(orderItem);
        }

        CustomerOrder savedOrder =
                customerOrderRepository.save(order);

        cart.clear();

        return savedOrder;
    }

    @Transactional
    public CustomerOrder validatePayment(Long orderId) {

        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Commande introuvable : " + orderId
                        )
                );

        if ("PAID".equals(order.getStatus())) {
            throw new IllegalArgumentException(
                    "Cette commande est déjà payée."
            );
        }

        for (OrderItem orderItem : order.getItems()) {

            Weapon weapon = weaponRepository.findById(
                            orderItem.getWeapon().getId()
                    )
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Produit introuvable : "
                                            + orderItem.getWeapon().getId()
                            )
                    );

            int quantity = orderItem.getQuantity();

            if (weapon.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "Stock insuffisant pour le produit : "
                                + weapon.getName()
                );
            }

            weapon.setStock(
                    weapon.getStock() - quantity
            );
        }

        order.setStatus("PAID");

        return customerOrderRepository.save(order);
    }

}