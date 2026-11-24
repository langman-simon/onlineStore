package com.hyperion.cart;

import com.hyperion.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartTest {

    @Test
    void shouldAddProductToCart() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice())
                .thenReturn(new BigDecimal("50.00"));

        Cart cart = new Cart();
        cart.addProduct(product, 2);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(2);
        assertThat(cart.getTotalPrice())
                .isEqualByComparingTo("100.00");
    }

    @Test
    void shouldMergeQuantityWhenProductAlreadyExists() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addProduct(product, 2);
        cart.addProduct(product, 3);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(5);
    }

    @Test
    void shouldRejectQuantityAboveStock() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(2);

        Cart cart = new Cart();

        assertThatThrownBy(() ->
                cart.addProduct(product, 3)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.cart.totalQuantityStock");
    }

    @Test
    void shouldRejectNonPositiveQuantity() {
        Product product = mock(Product.class);

        Cart cart = new Cart();

        assertThatThrownBy(() ->
                cart.addProduct(product, 0)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.cart.quantityPositive");
    }

    @Test
    void shouldRemoveProductWhenQuantityBecomesZero() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addProduct(product, 2);

        cart.updateQuantity(1L, 0);

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void shouldClearCart() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addProduct(product, 2);

        cart.clear();

        assertThat(cart.isEmpty()).isTrue();
    }
}