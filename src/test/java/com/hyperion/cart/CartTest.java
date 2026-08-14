package com.hyperion.cart;

import com.hyperion.model.Weapon;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartTest {

    @Test
    void shouldAddWeaponToCart() {
        Weapon weapon = mock(Weapon.class);
        when(weapon.getId()).thenReturn(1L);
        when(weapon.getStock()).thenReturn(10);
        when(weapon.getPrice())
                .thenReturn(new BigDecimal("50.00"));

        Cart cart = new Cart();
        cart.addWeapon(weapon, 2);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(2);
        assertThat(cart.getTotalPrice())
                .isEqualByComparingTo("100.00");
    }

    @Test
    void shouldMergeQuantityWhenWeaponAlreadyExists() {
        Weapon weapon = mock(Weapon.class);
        when(weapon.getId()).thenReturn(1L);
        when(weapon.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addWeapon(weapon, 2);
        cart.addWeapon(weapon, 3);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(5);
    }

    @Test
    void shouldRejectQuantityAboveStock() {
        Weapon weapon = mock(Weapon.class);
        when(weapon.getId()).thenReturn(1L);
        when(weapon.getStock()).thenReturn(2);

        Cart cart = new Cart();

        assertThatThrownBy(() ->
                cart.addWeapon(weapon, 3)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.cart.totalQuantityStock");
    }

    @Test
    void shouldRejectNonPositiveQuantity() {
        Weapon weapon = mock(Weapon.class);

        Cart cart = new Cart();

        assertThatThrownBy(() ->
                cart.addWeapon(weapon, 0)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.cart.quantityPositive");
    }

    @Test
    void shouldRemoveWeaponWhenQuantityBecomesZero() {
        Weapon weapon = mock(Weapon.class);
        when(weapon.getId()).thenReturn(1L);
        when(weapon.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addWeapon(weapon, 2);

        cart.updateQuantity(1L, 0);

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void shouldClearCart() {
        Weapon weapon = mock(Weapon.class);
        when(weapon.getId()).thenReturn(1L);
        when(weapon.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addWeapon(weapon, 2);

        cart.clear();

        assertThat(cart.isEmpty()).isTrue();
    }
}