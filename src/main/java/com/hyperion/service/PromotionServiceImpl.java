package com.hyperion.service;

import com.hyperion.cart.CartItem;
import com.hyperion.model.Promotion;
import com.hyperion.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final BigDecimal STANDARD_DELIVERY_FEE = new BigDecimal("50.00");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("2000");

    private static final BigDecimal TIER_2_THRESHOLD = new BigDecimal("6000");
    private static final BigDecimal TIER_3_THRESHOLD = new BigDecimal("15000");

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

        BigDecimal tierRate = tierRateFor(cartTotal);

        BigDecimal bestGlobalPromoRate = findActivePromotions().stream()
                .filter(Promotion::isGlobal)
                .filter(p -> p.getDiscountPercentage() != null)
                .map(p -> p.getDiscountPercentage().divide(new BigDecimal("100")))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalRate = tierRate.max(bestGlobalPromoRate);

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
            throw new IllegalArgumentException("Le montant du panier ne peut pas être nul.");
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
                .filter(Promotion::isGlobal)
                .anyMatch(Promotion::isFreeDelivery);

        return tierFreeDelivery || promoFreeDelivery;
    }

    @Override
    public BigDecimal calculateDiscount(List<CartItem> items, boolean authenticated) {
        return resolveBestDiscount(items, authenticated).amount();
    }

    @Override
    public String getAppliedDiscountLabel(List<CartItem> items, boolean authenticated) {
        return resolveBestDiscount(items, authenticated).label();
    }

    private record DiscountResult(BigDecimal amount, String label) {}

    private DiscountResult resolveBestDiscount(List<CartItem> items, boolean authenticated) {
        if (!authenticated || items == null || items.isEmpty()) {
            return new DiscountResult(BigDecimal.ZERO, null);
        }

        BigDecimal cartTotal = totalOf(items);
        List<Promotion> activePromotions = findActivePromotions();

        BigDecimal tierRate = tierRateFor(cartTotal);
        BigDecimal tierDiscount = cartTotal.multiply(tierRate);
        DiscountResult best = new DiscountResult(
                tierDiscount,
                tierRate.compareTo(BigDecimal.ZERO) > 0
                        ? "Réduction fidélité (-" + tierRate.multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString() + "%)"
                        : null
        );

        for (Promotion promotion : activePromotions) {
            if (!promotion.isGlobal() || promotion.getDiscountPercentage() == null) {
                continue;
            }

            BigDecimal rate = promotion.getDiscountPercentage().divide(new BigDecimal("100"));
            BigDecimal amount = cartTotal.multiply(rate);

            if (amount.compareTo(best.amount()) > 0) {
                best = new DiscountResult(amount, promotion.getTitle());
            }
        }

        for (Promotion promotion : activePromotions) {
            if (promotion.isGlobal() || promotion.getDiscountPercentage() == null) {
                continue;
            }

            BigDecimal matchingSubtotal = items.stream()
                    .filter(item -> promotion.appliesTo(item.getWeapon()))
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (matchingSubtotal.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            BigDecimal rate = promotion.getDiscountPercentage().divide(new BigDecimal("100"));
            BigDecimal amount = matchingSubtotal.multiply(rate);

            if (amount.compareTo(best.amount()) > 0) {
                best = new DiscountResult(amount, promotion.getTitle());
            }
        }

        return new DiscountResult(best.amount().setScale(2, RoundingMode.HALF_UP), best.label());
    }

    @Override
    public BigDecimal calculateFinalPrice(List<CartItem> items, boolean authenticated) {
        if (items == null) {
            throw new IllegalArgumentException("Le panier ne peut pas être nul.");
        }

        BigDecimal cartTotal = totalOf(items);
        BigDecimal discount = calculateDiscount(items, authenticated);
        BigDecimal deliveryFee = isFreeDeliveryApplied(items, authenticated)
                ? BigDecimal.ZERO
                : STANDARD_DELIVERY_FEE;

        BigDecimal finalPrice = cartTotal.subtract(discount).add(deliveryFee);
        return finalPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : finalPrice;
    }

    @Override
    public boolean isFreeDeliveryApplied(List<CartItem> items, boolean authenticated) {
        if (!authenticated || items == null || items.isEmpty()) {
            return false;
        }

        BigDecimal cartTotal = totalOf(items);
        boolean tierFreeDelivery = cartTotal.compareTo(FREE_DELIVERY_THRESHOLD) >= 0;

        boolean promoFreeDelivery = findActivePromotions().stream()
                .filter(Promotion::isFreeDelivery)
                .anyMatch(promotion -> promotion.isGlobal()
                        || items.stream().anyMatch(item -> promotion.appliesTo(item.getWeapon())));

        return tierFreeDelivery || promoFreeDelivery;
    }

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

    @Override
    public BigDecimal getStandardDeliveryFee() {
        return STANDARD_DELIVERY_FEE;
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

    private BigDecimal tierRateFor(BigDecimal cartTotal) {
        if (cartTotal.compareTo(TIER_3_THRESHOLD) >= 0) {
            return TIER_3_RATE;
        }
        if (cartTotal.compareTo(TIER_2_THRESHOLD) >= 0) {
            return TIER_2_RATE;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal totalOf(List<CartItem> items) {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<Promotion> findActivePromotions() {
        return promotionRepository.findAll().stream()
                .filter(Promotion::isCurrentlyValid)
                .toList();
    }
}