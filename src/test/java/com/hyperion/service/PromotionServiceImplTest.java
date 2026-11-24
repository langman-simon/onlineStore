package com.hyperion.service;

import com.hyperion.dataAccess.dao.PromotionDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromotionServiceImplTest {

    private PromotionDataAccess promotionDataAccess;
    private PromotionServiceImpl promotionService;

    @BeforeEach
    void setup() {
        promotionDataAccess = Mockito.mock(PromotionDataAccess.class);
        promotionService = new PromotionServiceImpl(promotionDataAccess);
    }

    @Test
    void shouldApplyNoDiscountWhenBelowFirstThreshold() {
        BigDecimal cartTotal = new BigDecimal("150.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApplyNoDiscountWhenNotAuthenticated() {
        BigDecimal cartTotal = new BigDecimal("400.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, false);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApply10PercentDiscountBetweenTier2AndTier3() {
        BigDecimal cartTotal = new BigDecimal("250.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo("25.00");
    }

    @Test
    void shouldApply10PercentDiscountExactlyAtTier2Threshold() {
        BigDecimal cartTotal = new BigDecimal("200.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo("20.00");
    }

    @Test
    void shouldApply15PercentDiscountAboveTier3Threshold() {
        BigDecimal cartTotal = new BigDecimal("400.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo("60.00");
    }

    @Test
    void shouldApply15PercentDiscountExactlyAtTier3Threshold() {
        BigDecimal cartTotal = new BigDecimal("350.00");

        BigDecimal discount = promotionService.calculateDiscount(cartTotal, true);

        assertThat(discount).isEqualByComparingTo("52.50");
    }

    @Test
    void shouldChargeStandardDeliveryFeeBelowThreshold() {
        BigDecimal cartTotal = new BigDecimal("50.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, true);

        assertThat(fee).isEqualByComparingTo("6.95");
    }

    @Test
    void shouldOfferFreeDeliveryAtExactThreshold() {
        BigDecimal cartTotal = new BigDecimal("100.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, true);

        assertThat(fee).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldOfferFreeDeliveryAboveThreshold() {
        BigDecimal cartTotal = new BigDecimal("350.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, true);

        assertThat(fee).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldChargeDeliveryFeeWhenNotAuthenticatedEvenAboveThreshold() {
        BigDecimal cartTotal = new BigDecimal("350.00");

        BigDecimal fee = promotionService.calculateDeliveryFee(cartTotal, false);

        assertThat(fee).isEqualByComparingTo("6.95");
    }

    @Test
    void shouldIndicateFreeDeliveryAppliedWhenEligible() {
        boolean result = promotionService.isFreeDeliveryApplied(new BigDecimal("100.00"), true);

        assertThat(result).isTrue();
    }

    @Test
    void shouldIndicateNoFreeDeliveryWhenBelowThreshold() {
        boolean result = promotionService.isFreeDeliveryApplied(new BigDecimal("99.99"), true);

        assertThat(result).isFalse();
    }

    @Test
    void shouldCalculateFinalPriceWithFeeOnlyBelowFreeDeliveryThreshold() {
        BigDecimal cartTotal = new BigDecimal("50.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        assertThat(finalPrice).isEqualByComparingTo("56.95");
    }

    @Test
    void shouldCalculateFinalPriceWithFreeDeliveryOnly() {
        BigDecimal cartTotal = new BigDecimal("150.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        assertThat(finalPrice).isEqualByComparingTo("150.00");
    }

    @Test
    void shouldCalculateFinalPriceWithDiscountAndFreeDelivery() {
        BigDecimal cartTotal = new BigDecimal("250.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        assertThat(finalPrice).isEqualByComparingTo("225.00");
    }

    @Test
    void shouldCalculateFinalPriceWithTopTierDiscount() {
        BigDecimal cartTotal = new BigDecimal("400.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, true);

        assertThat(finalPrice).isEqualByComparingTo("340.00");
    }

    @Test
    void shouldCalculateFinalPriceWithoutAnyPromotionWhenNotAuthenticated() {
        BigDecimal cartTotal = new BigDecimal("250.00");

        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartTotal, false);

        assertThat(finalPrice).isEqualByComparingTo("256.95");
    }

    @Test
    void shouldThrowWhenCartTotalIsNull() {
        assertThatThrownBy(() -> promotionService.calculateFinalPrice((BigDecimal) null, true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldSuggestNextTierWhenNoBetterDiscountApplies() {
        PromotionService.NextTierIncentive incentive =
                promotionService.getNextTierIncentive(new BigDecimal("150.00"), BigDecimal.ZERO);

        assertThat(incentive).isNotNull();
        assertThat(incentive.remainingAmount()).isEqualByComparingTo("50.00");
        assertThat(incentive.ratePercent()).isEqualByComparingTo("10");
    }

    @Test
    void shouldNotSuggestNextTierWhenCurrentDiscountIsBetter() {
        PromotionService.NextTierIncentive incentive =
                promotionService.getNextTierIncentive(new BigDecimal("24.99"), new BigDecimal("12.50"));

        assertThat(incentive).isNull();
    }
}
