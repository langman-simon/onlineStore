package com.hyperion.cart;

import com.hyperion.model.Weapon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addWeapon(Weapon weapon, int quantity) {
        if (weapon == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être nul.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "La quantité doit être supérieure à zéro."
            );
        }

        CartItem existingItem = findItemByWeaponId(weapon.getId());

        if (existingItem != null) {
            existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
            );
        } else {
            items.add(new CartItem(weapon, quantity));
        }
    }

    private CartItem findItemByWeaponId(Long weaponId) {
        if (weaponId == null) {
            return null;
        }

        return items.stream()
                .filter(item -> weaponId.equals(
                        item.getWeapon().getId()
                ))
                .findFirst()
                .orElse(null);
    }

    public int getTotalQuantity() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void removeWeapon(Long weaponId) {
        items.removeIf(item -> item.getWeapon().getId().equals(weaponId));
    }

    public void updateQuantity(Long weaponId, int quantity) {

        CartItem item = findItemByWeaponId(weaponId);

        if (item == null) {
            return;
        }

        if (quantity <= 0) {
            removeWeapon(weaponId);
        } else {
            item.setQuantity(quantity);
        }
    }

    public void clear() {
        items.clear();
    }

}