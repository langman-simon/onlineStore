package com.hyperion.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weapon_id", nullable = false)
    private Weapon weapon;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    public OrderItem() {
    }

    public OrderItem(Weapon weapon, int quantity) {
        this.weapon = weapon;
        this.quantity = quantity;
        this.unitPrice = weapon.getPrice();
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getQuantity() {
        return quantity;
    }

}