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
    public CustomerOrder validateOrder(Cart cart, boolean authenticated) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Le panier est vide.");
        }

        BigDecimal originalPrice = cart.getTotalPrice();
        BigDecimal discountAmount = promotionService.calculateDiscount(originalPrice, authenticated);
        BigDecimal finalPrice = promotionService.calculateFinalPrice(originalPrice, authenticated);

        CustomerOrder order = new CustomerOrder(originalPrice, discountAmount, finalPrice);

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

            weapon.setStock(weapon.getStock() - quantity);

            OrderItem orderItem = new OrderItem(weapon, quantity);
            order.addItem(orderItem);
        }

        CustomerOrder savedOrder = customerOrderRepository.save(order);
        cart.clear();

        return savedOrder;
    }
}