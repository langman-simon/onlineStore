package com.hyperion.cart;

import com.hyperion.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartItemTest {

    @Test
    void shouldCalculateSubtotal() {
        Product weapon = mock(Product.class);
        when(weapon.getPrice())
                .thenReturn(new BigDecimal("50.00"));

        CartItem item = new CartItem(weapon, 3);

        assertThat(item.getSubtotal())
                .isEqualByComparingTo("150.00");
    }

    @Test
    void shouldUpdateQuantity() {
        Product weapon = mock(Product.class);
        CartItem item = new CartItem(weapon, 1);

        item.setQuantity(4);

        assertThat(item.getQuantity()).isEqualTo(4);
    }
}