package com.hyperion.controller;

import com.hyperion.model.Product;
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

    @PostMapping("/cart/add/{productId}")
    public String addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session
    ) {
        Optional<Product> productOpt =
                catalogueService.findWeaponById(productId);

        if (productOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.product.notFound"
            );

            return "redirect:/catalogue";
        }

        Product product = productOpt.get();

        if (quantity <= 0) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityPositive"
            );

            return "redirect:/products/" + productId;
        }

        if (quantity > product.getStock()) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityStock"
            );

            return "redirect:/products/" + productId;
        }

        try {
            sessionCart.getCart().addProduct(
                    product,
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

            return "redirect:/products/" + productId;
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
        model.addAttribute("cart", sessionCart.getCart());
        model.addAttribute("originalPrice", originalPrice);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("discountLabel", discountLabel);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("freeDelivery", freeDelivery);
        model.addAttribute("standardDeliveryFee", standardDeliveryFee);

        BigDecimal remainingForFreeDelivery =
                promotionService.getRemainingForFreeDelivery(originalPrice, freeDelivery);

        PromotionService.NextTierIncentive nextTierIncentive =
                promotionService.getNextTierIncentive(originalPrice);

        BigDecimal remainingForNextTier =
                nextTierIncentive != null ? nextTierIncentive.remainingAmount() : null;

        BigDecimal nextTierRate =
                nextTierIncentive != null ? nextTierIncentive.ratePercent() : null;

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

        return "cart/cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(
            @PathVariable Long productId,
            HttpSession session
    ) {
        try {
            sessionCart.getCart().removeProduct(productId);

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

    @PostMapping("/cart/update/{productId}")
    public String updateQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity,
            HttpSession session
    ) {
        Optional<Product> productOpt =
                catalogueService.findWeaponById(productId);

        if (productOpt.isEmpty()) {
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

        if (quantity > productOpt.get().getStock()) {
            globalBannerService.error(
                    session,
                    "error.cart.quantityStock"
            );

            return "redirect:/cart";
        }

        try {
            sessionCart.getCart().updateQuantity(
                    productId,
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