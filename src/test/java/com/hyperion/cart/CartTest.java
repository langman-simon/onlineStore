package com.hyperion.cart;

import com.hyperion.model.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartTest {

    private Cart cart;
    private Weapon weapon;

    @BeforeEach
    void setUp() {
        cart = new Cart();

        weapon = mock(Weapon.class);

        when(weapon.getId()).thenReturn(1L);
        when(weapon.getName()).thenReturn("Produit test");
        when(weapon.getPrice()).thenReturn(new BigDecimal("100.00"));
        when(weapon.getStock()).thenReturn(10);
    }

    @Test
    void shouldAddWeaponToCart() {
        // Arrange
        int quantity = 2;

        // Act
        cart.addWeapon(weapon, quantity);

        // Assert
        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(2);
        assertThat(cart.getTotalPrice())
                .isEqualByComparingTo("200.00");
    }

    @Test
    void shouldIncreaseQuantityWhenWeaponAlreadyExists() {
        // Arrange
        cart.addWeapon(weapon, 2);

        // Act
        cart.addWeapon(weapon, 3);

        // Assert
        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(5);
    }

    @Test
    void shouldRejectQuantityAboveStock() {
        // Arrange
        int quantity = 11;

        // Act / Assert
        assertThatThrownBy(() -> cart.addWeapon(weapon, quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La quantité totale dépasse le stock disponible.");
    }

    @Test
    void shouldRejectZeroQuantity() {
        assertThatThrownBy(() -> cart.addWeapon(weapon, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La quantité doit être supérieure à zéro.");
    }

    @Test
    void shouldUpdateQuantity() {
        // Arrange
        cart.addWeapon(weapon, 2);

        // Act
        cart.updateQuantity(weapon.getId(), 5);

        // Assert
        assertThat(cart.getTotalQuantity()).isEqualTo(5);
    }

    @Test
    void shouldRemoveWeaponWhenUpdatedQuantityIsZero() {
        // Arrange
        cart.addWeapon(weapon, 2);

        // Act
        cart.updateQuantity(weapon.getId(), 0);

        // Assert
        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void shouldRemoveWeapon() {
        // Arrange
        cart.addWeapon(weapon, 1);

        // Act
        cart.removeWeapon(weapon.getId());

        // Assert
        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void shouldClearCart() {
        // Arrange
        cart.addWeapon(weapon, 2);

        // Act
        cart.clear();

        // Assert
        assertThat(cart.getItems()).isEmpty();
        assertThat(cart.getTotalQuantity()).isZero();
        assertThat(cart.getTotalPrice())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }
}