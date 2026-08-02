package com.hyperion.service;

import java.math.BigDecimal;

public interface PromotionService {
    BigDecimal calculateDiscount(BigDecimal cartTotal, boolean authenticated);
    BigDecimal calculateFinalPrice(BigDecimal cartTotal, boolean authenticated);
}