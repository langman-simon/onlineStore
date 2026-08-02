package com.hyperion.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final BigDecimal THRESHOLD = new BigDecimal("2000");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal calculateDiscount(BigDecimal cartTotal, boolean authenticated) {
        if (!authenticated || cartTotal == null || cartTotal.compareTo(THRESHOLD) < 0) {
            return BigDecimal.ZERO;
        }
        return cartTotal.multiply(DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal cartTotal, boolean authenticated) {
        if (cartTotal == null) {
            throw new IllegalArgumentException("Le montant du panier ne peut pas être nul.");
        }
        BigDecimal discount = calculateDiscount(cartTotal, authenticated);
        BigDecimal finalPrice = cartTotal.subtract(discount);
        return finalPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : finalPrice;
    }
}