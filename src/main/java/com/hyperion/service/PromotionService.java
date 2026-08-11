package com.hyperion.service;

import com.hyperion.model.Promotion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PromotionService {

    BigDecimal calculateDiscount(BigDecimal cartTotal, boolean authenticated);
    BigDecimal calculateDeliveryFee(BigDecimal cartTotal, boolean authenticated);
    BigDecimal calculateFinalPrice(BigDecimal cartTotal, boolean authenticated);
    boolean isFreeDeliveryApplied(BigDecimal cartTotal, boolean authenticated);

    List<Promotion> findAll();
    Optional<Promotion> findById(Long id);
    Promotion save(Promotion promotion);
    void deleteById(Long id);

    BigDecimal getFreeDeliveryThreshold();
    BigDecimal getTier2Threshold();
    BigDecimal getTier2RatePercent();
    BigDecimal getTier3Threshold();
    BigDecimal getTier3RatePercent();
    BigDecimal getStandardDeliveryFee();
}