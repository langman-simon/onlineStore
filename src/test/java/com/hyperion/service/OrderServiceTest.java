package com.hyperion.service;

import com.hyperion.cart.Cart;
import com.hyperion.model.CustomerOrder;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.repository.WeaponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private WeaponRepository weaponRepository;
    private CustomerOrderRepository customerOrderRepository;
    private OrderService orderService;

    private Weapon weapon;

    @BeforeEach
    void setUp() {
        weaponRepository = mock(WeaponRepository.class);
        customerOrderRepository = mock(CustomerOrderRepository.class);

        orderService = new OrderService(
                weaponRepository,
                customerOrderRepository
        );

        weapon = mock(Weapon.class);

        when(weapon.getId()).thenReturn(1L);
        when(weapon.getName()).thenReturn("Produit test");
        when(weapon.getPrice()).thenReturn(new BigDecimal("100.00"));
        when(weapon.getStock()).thenReturn(10);
    }

    @Test
    void shouldValidateOrderAndDecreaseStock() {
        // Arrange
        Cart cart = new Cart();
        cart.addWeapon(weapon, 2);

        when(weaponRepository.findById(1L))
                .thenReturn(Optional.of(weapon));

        when(customerOrderRepository.save(any(CustomerOrder.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CustomerOrder order = orderService.validateOrder(cart);

        // Assert
        verify(weapon).setStock(8);
        verify(customerOrderRepository)
                .save(any(CustomerOrder.class));

        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getTotalPrice())
                .isEqualByComparingTo("200.00");

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void shouldRejectEmptyCart() {
        // Arrange
        Cart cart = new Cart();

        // Act / Assert
        assertThatThrownBy(() -> orderService.validateOrder(cart))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le panier est vide.");

        verifyNoInteractions(
                weaponRepository,
                customerOrderRepository
        );
    }

    @Test
    void shouldRejectOrderWhenWeaponDoesNotExist() {
        // Arrange
        Cart cart = new Cart();
        cart.addWeapon(weapon, 1);

        when(weaponRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act / Assert
        assertThatThrownBy(() -> orderService.validateOrder(cart))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Produit introuvable : 1");

        verify(customerOrderRepository, never())
                .save(any(CustomerOrder.class));

        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void shouldRejectOrderWhenStockIsInsufficient() {
        // Arrange
        Cart cart = new Cart();
        cart.addWeapon(weapon, 5);

        Weapon databaseWeapon = mock(Weapon.class);

        when(databaseWeapon.getId()).thenReturn(1L);
        when(databaseWeapon.getName()).thenReturn("Produit test");
        when(databaseWeapon.getPrice())
                .thenReturn(new BigDecimal("100.00"));
        when(databaseWeapon.getStock()).thenReturn(3);

        when(weaponRepository.findById(1L))
                .thenReturn(Optional.of(databaseWeapon));

        // Act / Assert
        assertThatThrownBy(() -> orderService.validateOrder(cart))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "Stock insuffisant pour le produit : Produit test"
                );

        verify(databaseWeapon, never()).setStock(anyInt());
        verify(customerOrderRepository, never())
                .save(any(CustomerOrder.class));

        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void shouldCreateOneOrderItemPerCartItem() {
        // Arrange
        Weapon secondWeapon = mock(Weapon.class);

        when(secondWeapon.getId()).thenReturn(2L);
        when(secondWeapon.getName()).thenReturn("Deuxième produit");
        when(secondWeapon.getPrice())
                .thenReturn(new BigDecimal("50.00"));
        when(secondWeapon.getStock()).thenReturn(10);

        Cart cart = new Cart();
        cart.addWeapon(weapon, 2);
        cart.addWeapon(secondWeapon, 3);

        when(weaponRepository.findById(1L))
                .thenReturn(Optional.of(weapon));
        when(weaponRepository.findById(2L))
                .thenReturn(Optional.of(secondWeapon));

        when(customerOrderRepository.save(any(CustomerOrder.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CustomerOrder order = orderService.validateOrder(cart);

        // Assert
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getTotalPrice())
                .isEqualByComparingTo("350.00");

        verify(weapon).setStock(8);
        verify(secondWeapon).setStock(7);
        assertThat(cart.isEmpty()).isTrue();
    }
}