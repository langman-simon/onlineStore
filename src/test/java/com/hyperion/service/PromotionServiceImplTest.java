package com.hyperion.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromotionServiceImplTest {

    private final PromotionServiceImpl promotionService = new PromotionServiceImpl();

    @Test
    void shouldApplyNoDiscountWhenBelowThreshold() {
        BigDecimal cartTotal = new BigDecimal("1500.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApplyDiscountWhenAboveThresholdAndAuthenticated() {
        BigDecimal cartTotal = new BigDecimal("2500.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        // 10% de 2500 = 250.00
        assertThat(discount).isEqualByComparingTo("250.00");
    }

    @Test
    void shouldApplyNoDiscountWhenNotAuthenticated() {
        BigDecimal cartTotal = new BigDecimal("2500.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, false);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApplyDiscountExactlyAtThreshold() {
        BigDecimal cartTotal = new BigDecimal("2000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        // 10% de 2000 = 200.00 (seuil inclus)
        assertThat(discount).isEqualByComparingTo("200.00");
    }

    @Test
    void shouldCalculateFinalPriceWithDiscount() {
        BigDecimal cartTotal = new BigDecimal("3000.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        // 3000 - 10% (300) = 2700.00
        assertThat(finalPrice).isEqualByComparingTo("2700.00");
    }

    @Test
    void shouldCalculateFinalPriceWithoutDiscount() {
        BigDecimal cartTotal = new BigDecimal("500.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        assertThat(finalPrice).isEqualByComparingTo("500.00");
    }

    @Test
    void shouldThrowWhenCartTotalIsNull() {
        assertThatThrownBy(() -> promotionService.calculateFinalPrice(null, true))
                .isInstanceOf(IllegalArgumentException.class);
    }
}