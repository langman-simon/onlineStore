package com.hyperion.cart;

import com.hyperion.dataAccess.entity.ProductEntity;

import java.math.BigDecimal;

public class CartItem {

    private final ProductEntity weapon;
    private int quantity;

    public CartItem(ProductEntity weapon, int quantity) {
        this.weapon = weapon;
        this.quantity = quantity;
    }

    public ProductEntity getWeapon() {
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