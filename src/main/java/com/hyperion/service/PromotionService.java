package com.hyperion.service;

import com.hyperion.cart.CartItem;
import com.hyperion.model.Promotion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PromotionService {

    BigDecimal calculateDiscount(BigDecimal cartTotal, boolean authenticated);
    BigDecimal calculateDeliveryFee(BigDecimal cartTotal, boolean authenticated);
    BigDecimal calculateFinalPrice(BigDecimal cartTotal, boolean authenticated);
    boolean isFreeDeliveryApplied(BigDecimal cartTotal, boolean authenticated);
    BigDecimal calculateDiscount(List<CartItem> items, boolean authenticated);
    BigDecimal calculateFinalPrice(List<CartItem> items, boolean authenticated);
    boolean isFreeDeliveryApplied(List<CartItem> items, boolean authenticated);
    String getAppliedDiscountLabel(List<CartItem> items, boolean authenticated);

    List<Promotion> findAll();
    Optional<Promotion> findById(Long id);
    Promotion save(Promotion promotion);
    void deleteById(Long id);

    BigDecimal getStandardDeliveryFee();
    BigDecimal getFreeDeliveryThreshold();
    BigDecimal getTier2Threshold();
    BigDecimal getTier2RatePercent();
    BigDecimal getTier3Threshold();
    BigDecimal getTier3RatePercent();
}