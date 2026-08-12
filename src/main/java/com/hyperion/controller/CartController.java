package com.hyperion.controller;

import com.hyperion.model.Weapon;
import com.hyperion.service.CatalogueService;
import com.hyperion.service.GlobalBannerService;
import com.hyperion.service.PromotionService;
import com.hyperion.session.SessionCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class CartController {

    private final SessionCart sessionCart;
    private final CatalogueService catalogueService;
    private final PromotionService promotionService;
    private final GlobalBannerService globalBannerService;

    public CartController(
            SessionCart sessionCart,
            CatalogueService catalogueService,
            PromotionService promotionService,
            GlobalBannerService globalBannerService
    ) {
        this.sessionCart = sessionCart;
        this.catalogueService = catalogueService;
        this.promotionService = promotionService;
        this.globalBannerService = globalBannerService;
    }

    @PostMapping("/cart/add/{weaponId}")
    public String addToCart(
            @PathVariable Long weaponId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session
    ) {
        Optional<Weapon> weaponOpt =
                catalogueService.findWeaponById(weaponId);

        if (weaponOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.product.notFound"
            );

            return "redirect:/catalogue";
        }

        Weapon weapon = weaponOpt.get();

        if (quantity <= 0) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityPositive"
            );

            return "redirect:/weapons/" + weaponId;
        }

        if (quantity > weapon.getStock()) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityStock"
            );

            return "redirect:/weapons/" + weaponId;
        }

        try {
            sessionCart.getCart().addWeapon(
                    weapon,
                    quantity
            );

            globalBannerService.success(
                    session,
                    "message.cart.added"
            );

            return "redirect:/cart";

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );

            return "redirect:/weapons/" + weaponId;
        }
    }

    @GetMapping("/cart")
    public String showCart(
            Model model,
            Authentication authentication
    ) {
        boolean authenticated = isAuthenticated(authentication);

        var cartItems = sessionCart.getCart().getItems();

        BigDecimal originalPrice = sessionCart.getCart().getTotalPrice();
        BigDecimal discountAmount = promotionService.calculateDiscount(cartItems, authenticated);
        String discountLabel = promotionService.getAppliedDiscountLabel(cartItems, authenticated);
        BigDecimal finalPrice = promotionService.calculateFinalPrice(cartItems, authenticated);
        boolean freeDelivery = promotionService.isFreeDeliveryApplied(cartItems, authenticated);
        BigDecimal standardDeliveryFee = promotionService.getStandardDeliveryFee();
        BigDecimal deliveryFee = freeDelivery ? BigDecimal.ZERO : standardDeliveryFee;

        model.addAttribute("titleKey", "page.cart");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/cart/cart.jsp"
        );
        model.addAttribute("cart", sessionCart.getCart());
        model.addAttribute("originalPrice", originalPrice);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("discountLabel", discountLabel);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("freeDelivery", freeDelivery);
        model.addAttribute("standardDeliveryFee", standardDeliveryFee);

        BigDecimal freeDeliveryThreshold =
                promotionService.getFreeDeliveryThreshold();
                promotionService.getFreeDeliveryThreshold();

        BigDecimal remainingForFreeDelivery = freeDelivery
                ? BigDecimal.ZERO
                : freeDeliveryThreshold
                .subtract(originalPrice)
                .max(BigDecimal.ZERO);

        BigDecimal tier2Threshold =
                promotionService.getTier2Threshold();

        BigDecimal tier3Threshold =
                promotionService.getTier3Threshold();

        BigDecimal remainingForNextTier;
        BigDecimal nextTierRate;

        if (originalPrice.compareTo(tier3Threshold) >= 0) {
            remainingForNextTier = null;
            nextTierRate = null;

        } else if (originalPrice.compareTo(tier2Threshold) >= 0) {
            remainingForNextTier =
                    tier3Threshold.subtract(originalPrice);

            nextTierRate =
                    promotionService.getTier3RatePercent();

        } else {
            remainingForNextTier =
                    tier2Threshold.subtract(originalPrice);

            nextTierRate =
                    promotionService.getTier2RatePercent();
        }

        model.addAttribute(
                "remainingForFreeDelivery",
                remainingForFreeDelivery
        );
        model.addAttribute(
                "remainingForNextTier",
                remainingForNextTier
        );
        model.addAttribute(
                "nextTierRate",
                nextTierRate
        );

        return "template/template";
    }

    @PostMapping("/cart/remove/{weaponId}")
    public String removeFromCart(
            @PathVariable Long weaponId,
            HttpSession session
    ) {
        try {
            sessionCart.getCart().removeWeapon(weaponId);

            globalBannerService.success(
                    session,
                    "message.cart.removed"
            );

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{weaponId}")
    public String updateQuantity(
            @PathVariable Long weaponId,
            @RequestParam int quantity,
            HttpSession session
    ) {
        Optional<Weapon> weaponOpt =
                catalogueService.findWeaponById(weaponId);

        if (weaponOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.product.notFound"
            );

            return "redirect:/cart";
        }

        if (quantity <= 0) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityPositive"
            );

            return "redirect:/cart";
        }

        if (quantity > weaponOpt.get().getStock()) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityStock"
            );

            return "redirect:/cart";
        }

        try {
            sessionCart.getCart().updateQuantity(
                    weaponId,
                    quantity
            );

            globalBannerService.success(
                    session,
                    "message.cart.updated"
            );

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        sessionCart.getCart().clear();

        globalBannerService.success(
                session,
                "message.cart.cleared"
        );

        return "redirect:/cart";
    }

    private boolean isAuthenticated(
            Authentication authentication
    ) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication
                instanceof AnonymousAuthenticationToken);
    }
}