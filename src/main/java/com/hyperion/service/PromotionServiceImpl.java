package com.hyperion.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("500.00");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("3000");

    private static final BigDecimal TIER_2_THRESHOLD = new BigDecimal("10000");
    private static final BigDecimal TIER_3_THRESHOLD = new BigDecimal("50000");

    private static final BigDecimal TIER_2_RATE = new BigDecimal("0.10");
    private static final BigDecimal TIER_3_RATE = new BigDecimal("0.15");

    @Override
    public BigDecimal calculateDiscount(BigDecimal cartTotal, boolean authenticated) {
        if (!authenticated || cartTotal == null || cartTotal.compareTo(TIER_2_THRESHOLD) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal rate = cartTotal.compareTo(TIER_3_THRESHOLD) >= 0 ? TIER_3_RATE : TIER_2_RATE;
        return cartTotal.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateDeliveryFee(BigDecimal cartTotal, boolean authenticated) {
        if (isFreeDeliveryApplied(cartTotal, authenticated)) {
            return BigDecimal.ZERO;
        }
        return STANDARD_DELIVERY_FEE;
    }

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal cartTotal, boolean authenticated) {
        if (cartTotal == null) {
            throw new IllegalArgumentException("Le montant du panier ne peut pas être nul.");
        }
        BigDecimal discount = calculateDiscount(cartTotal, authenticated);
        BigDecimal deliveryFee = calculateDeliveryFee(cartTotal, authenticated);

        BigDecimal finalPrice = cartTotal.subtract(discount).add(deliveryFee);
        return finalPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : finalPrice;
    }

    @Override
    public boolean isFreeDeliveryApplied(BigDecimal cartTotal, boolean authenticated) {
        return authenticated && cartTotal != null && cartTotal.compareTo(FREE_DELIVERY_THRESHOLD) >= 0;
    }
}