package com.hyperion.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "free_delivery", nullable = false)
    private boolean freeDelivery;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_category_id")
    private Category targetCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_weapon_id")
    private Weapon targetWeapon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(boolean freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(Category targetCategory) {
        this.targetCategory = targetCategory;
    }

    public Weapon getTargetWeapon() {
        return targetWeapon;
    }

    public void setTargetWeapon(Weapon targetWeapon) {
        this.targetWeapon = targetWeapon;
    }

    public boolean isGlobal() {
        return targetCategory == null && targetWeapon == null;
    }

    public boolean appliesTo(Weapon weapon) {
        if (isGlobal()) {
            return true;
        }

        if (targetWeapon != null) {
            return targetWeapon.getId().equals(weapon.getId());
        }

        return targetCategory != null
                && weapon.getCategory() != null
                && targetCategory.getId().equals(weapon.getCategory().getId());
    }

    public boolean isCurrentlyValid() {
        LocalDate today = LocalDate.now();
        boolean afterStart = startDate == null || !today.isBefore(startDate);
        boolean beforeEnd = endDate == null || !today.isAfter(endDate);
        return active && afterStart && beforeEnd;
    }
}