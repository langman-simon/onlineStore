package com.hyperion.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Promotion {

    private Long id;
    private String title;
    private String description;
    private BigDecimal discountPercentage;
    private boolean freeDelivery;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active = true;
    private Category targetCategory;
    private Product targetProduct;

    public Promotion() {
    }

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

    public Product getTargetProduct() {
        return targetProduct;
    }

    public void setTargetProduct(Product targetProduct) {
        this.targetProduct = targetProduct;
    }

    public boolean isGlobal() {
        return targetCategory == null && targetProduct == null;
    }

    public boolean appliesTo(Product product) {
        if (isGlobal()) {
            return true;
        }

        if (targetProduct != null) {
            return targetProduct.getId().equals(product.getId());
        }

        return product.getCategory() != null
                && targetCategory.getId().equals(product.getCategory().getId());
    }

    public boolean isCurrentlyValid() {
        LocalDate today = LocalDate.now();
        boolean afterStart = startDate == null || !today.isBefore(startDate);
        boolean beforeEnd = endDate == null || !today.isAfter(endDate);
        return active && afterStart && beforeEnd;
    }
}