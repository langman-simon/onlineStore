package com.hyperion.cart;

import com.hyperion.model.Product;

import java.math.BigDecimal;

public class CartItem {

    private final Product weapon;
    private int quantity;

    public CartItem(Product weapon, int quantity) {
        this.weapon = weapon;
        this.quantity = quantity;
    }

    public Product getWeapon() {
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