package com.hyperion.service;

import com.hyperion.cart.CartItem;
import com.hyperion.dataAccess.entity.PromotionEntity;

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

    List<PromotionEntity> findAll();
    Optional<PromotionEntity> findById(Long id);
    PromotionEntity save(PromotionEntity promotion);
    void deleteById(Long id);

    BigDecimal getStandardDeliveryFee();
    BigDecimal getFreeDeliveryThreshold();
    BigDecimal getTier2Threshold();
    BigDecimal getTier2RatePercent();
    BigDecimal getTier3Threshold();
    BigDecimal getTier3RatePercent();
    BigDecimal getRemainingForFreeDelivery(BigDecimal cartTotal, boolean freeDeliveryApplied);
    NextTierIncentive getNextTierIncentive(BigDecimal cartTotal);

    record NextTierIncentive(BigDecimal remainingAmount, BigDecimal ratePercent) {}
}