package com.hyperion.service;

import com.hyperion.model.Promotion;
import com.hyperion.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("50.00");   // avant 500.00
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("2000");  // avant 3000

    private static final BigDecimal TIER_2_THRESHOLD = new BigDecimal("6000");         // avant 10000
    private static final BigDecimal TIER_3_THRESHOLD = new BigDecimal("15000");
    // avant 50000
    private static final BigDecimal TIER_2_RATE = new BigDecimal("0.10");
    private static final BigDecimal TIER_3_RATE = new BigDecimal("0.15");

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal cartTotal, boolean authenticated) {
        if (!authenticated || cartTotal == null) {
            return BigDecimal.ZERO;
        }

        // 1. Taux fidélité par palier (inchangé, basé sur le montant du panier)
        BigDecimal tierRate = BigDecimal.ZERO;
        if (cartTotal.compareTo(TIER_3_THRESHOLD) >= 0) {
            tierRate = TIER_3_RATE;
        } else if (cartTotal.compareTo(TIER_2_THRESHOLD) >= 0) {
            tierRate = TIER_2_RATE;
        }

        // 2. Meilleure promotion active créée par l'admin (non cumulable entre elles)
        BigDecimal bestPromoRate = findActivePromotions().stream()
                .filter(p -> p.getDiscountPercentage() != null)
                .map(p -> p.getDiscountPercentage().divide(new BigDecimal("100")))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 3. On applique le meilleur des deux (fidélité vs promo admin), pas un cumul
        BigDecimal totalRate = tierRate.max(bestPromoRate);

        return cartTotal.multiply(totalRate).setScale(2, RoundingMode.HALF_UP);
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
            throw new IllegalArgumentException("error.promotion.cartAmountNull");
        }

        BigDecimal discount = calculateDiscount(cartTotal, authenticated);
        BigDecimal deliveryFee = calculateDeliveryFee(cartTotal, authenticated);

        BigDecimal finalPrice = cartTotal.subtract(discount).add(deliveryFee);
        return finalPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : finalPrice;
    }

    @Override
    public boolean isFreeDeliveryApplied(BigDecimal cartTotal, boolean authenticated) {
        if (!authenticated || cartTotal == null) {
            return false;
        }

        boolean tierFreeDelivery = cartTotal.compareTo(FREE_DELIVERY_THRESHOLD) >= 0;
        boolean promoFreeDelivery = findActivePromotions().stream()
                .anyMatch(Promotion::isFreeDelivery);

        return tierFreeDelivery || promoFreeDelivery;
    }

    // --- Gestion CRUD des promotions (pour l'admin) ---

    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> findById(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public void deleteById(Long id) {
        promotionRepository.deleteById(id);
    }

    private List<Promotion> findActivePromotions() {
        return promotionRepository.findAll().stream()
                .filter(Promotion::isCurrentlyValid)
                .toList();
    }

    @Override
    public BigDecimal getFreeDeliveryThreshold() {
        return FREE_DELIVERY_THRESHOLD;
    }

    @Override
    public BigDecimal getTier2Threshold() {
        return TIER_2_THRESHOLD;
    }

    @Override
    public BigDecimal getTier2RatePercent() {
        return TIER_2_RATE.multiply(new BigDecimal("100"));
    }

    @Override
    public BigDecimal getTier3Threshold() {
        return TIER_3_THRESHOLD;
    }

    @Override
    public BigDecimal getTier3RatePercent() {
        return TIER_3_RATE.multiply(new BigDecimal("100"));
    }
}