package com.hyperion.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromotionServiceImplTest {

    private final PromotionServiceImpl promotionService = new PromotionServiceImpl();

    // --- calculateDiscount ---

    @Test
    void shouldApplyNoDiscountWhenBelowFirstThreshold() {
        BigDecimal cartTotal = new BigDecimal("5000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApplyNoDiscountWhenNotAuthenticated() {
        BigDecimal cartTotal = new BigDecimal("60000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, false);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApply10PercentDiscountBetweenTier2AndTier3() {
        BigDecimal cartTotal = new BigDecimal("20000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        // 10% de 20000 = 2000.00
        assertThat(discount).isEqualByComparingTo("2000.00");
    }

    @Test
    void shouldApply10PercentDiscountExactlyAtTier2Threshold() {
        BigDecimal cartTotal = new BigDecimal("10000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo("1000.00");
    }

    @Test
    void shouldApply15PercentDiscountAboveTier3Threshold() {
        BigDecimal cartTotal = new BigDecimal("100000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        // 15% de 100000 = 15000.00
        assertThat(discount).isEqualByComparingTo("15000.00");
    }

    @Test
    void shouldApply15PercentDiscountExactlyAtTier3Threshold() {
        BigDecimal cartTotal = new BigDecimal("50000.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo("7500.00");
    }

    // --- calculateDeliveryFee / isFreeDeliveryApplied ---

    @Test
    void shouldChargeStandardDeliveryFeeBelowThreshold() {
        BigDecimal cartTotal = new BigDecimal("2000.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, true);

        assertThat(fee).isEqualByComparingTo("500.00");
    }

    @Test
    void shouldOfferFreeDeliveryAtExactThreshold() {
        BigDecimal cartTotal = new BigDecimal("3000.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, true);

        assertThat(fee).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldOfferFreeDeliveryAboveThreshold() {
        BigDecimal cartTotal = new BigDecimal("15000.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, true);

        assertThat(fee).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldChargeDeliveryFeeWhenNotAuthenticatedEvenAboveThreshold() {
        BigDecimal cartTotal = new BigDecimal("15000.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, false);

        assertThat(fee).isEqualByComparingTo("500.00");
    }

    @Test
    void shouldIndicateFreeDeliveryAppliedWhenEligible() {
        boolean result = promotionService.isFreeDeliveryApplied(new BigDecimal("3000.00"), true);

        assertThat(result).isTrue();
    }

    @Test
    void shouldIndicateNoFreeDeliveryWhenBelowThreshold() {
        boolean result = promotionService.isFreeDeliveryApplied(new BigDecimal("2999.99"), true);

        assertThat(result).isFalse();
    }

    // --- calculateFinalPrice ---

    @Test
    void shouldCalculateFinalPriceWithFeeOnlyBelowFreeDeliveryThreshold() {
        BigDecimal cartTotal = new BigDecimal("1000.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        // 1000 - 0 (pas de réduction) + 500 (livraison) = 1500.00
        assertThat(finalPrice).isEqualByComparingTo("1500.00");
    }

    @Test
    void shouldCalculateFinalPriceWithFreeDeliveryOnly() {
        BigDecimal cartTotal = new BigDecimal("5000.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        // 5000 - 0 + 0 = 5000.00
        assertThat(finalPrice).isEqualByComparingTo("5000.00");
    }

    @Test
    void shouldCalculateFinalPriceWithDiscountAndFreeDelivery() {
        BigDecimal cartTotal = new BigDecimal("20000.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        // 20000 - 2000 (10%) + 0 (livraison offerte) = 18000.00
        assertThat(finalPrice).isEqualByComparingTo("18000.00");
    }

    @Test
    void shouldCalculateFinalPriceWithTopTierDiscount() {
        BigDecimal cartTotal = new BigDecimal("100000.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        // 100000 - 15000 (15%) + 0 = 85000.00
        assertThat(finalPrice).isEqualByComparingTo("85000.00");
    }

    @Test
    void shouldCalculateFinalPriceWithoutAnyPromotionWhenNotAuthenticated() {
        BigDecimal cartTotal = new BigDecimal("20000.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, false);

        // 20000 - 0 + 500 (livraison payante) = 20500.00
        assertThat(finalPrice).isEqualByComparingTo("20500.00");
    }

    @Test
    void shouldThrowWhenCartTotalIsNull() {
        assertThatThrownBy(() -> promotionService.calculateFinalPrice(null, true))
                .isInstanceOf(IllegalArgumentException.class);
    }
}