package com.hyperion.cart;

import com.hyperion.model.Weapon;

import java.math.BigDecimal;

public class CartItem {

    private final Weapon weapon;
    private int quantity;

    public CartItem(Weapon weapon, int quantity) {
        this.weapon = weapon;
        this.quantity = quantity;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return weapon.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }
}