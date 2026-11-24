package com.hyperion.cart;

import com.hyperion.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("error.cart.productNull");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "error.cart.quantityPositive"
            );
        }

        CartItem existingItem = findItemByProductId(product.getId());

        int currentQuantity = existingItem == null
                ? 0
                : existingItem.getQuantity();

        int newQuantity = currentQuantity + quantity;

        if (newQuantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "error.cart.totalQuantityStock"
            );
        }

        if (existingItem != null) {
            existingItem.setQuantity(newQuantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    private CartItem findItemByProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return items.stream()
                .filter(item -> productId.equals(
                        item.getProduct().getId()
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

    public void removeProduct(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void updateQuantity(Long productId, int quantity) {

        CartItem item = findItemByProductId(productId);

        if (item == null) {
            return;
        }

        if (quantity <= 0) {
            removeProduct(productId);
        } else {
            item.setQuantity(quantity);
        }
    }

    public void clear() {
        items.clear();
    }
}