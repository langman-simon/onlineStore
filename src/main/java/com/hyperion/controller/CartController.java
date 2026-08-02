package com.hyperion.controller;

import com.hyperion.model.Weapon;
import com.hyperion.repository.WeaponRepository;
import com.hyperion.service.PromotionService;
import com.hyperion.session.SessionCart;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class CartController {

    private final SessionCart sessionCart;
    private final WeaponRepository weaponRepository;
    private final PromotionService promotionService;

    public CartController(
            SessionCart sessionCart,
            WeaponRepository weaponRepository,
            PromotionService promotionService
    ) {
        this.sessionCart = sessionCart;
        this.weaponRepository = weaponRepository;
        this.promotionService = promotionService;
    }

    @PostMapping("/cart/add/{weaponId}")
    public String addToCart(
            @PathVariable Long weaponId,
            @RequestParam(defaultValue = "1") int quantity,
            RedirectAttributes redirectAttributes
    ) {
        Weapon weapon = weaponRepository.findById(weaponId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Produit introuvable : " + weaponId
                        )
                );

        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "La quantité doit être supérieure à zéro."
            );

            return "redirect:/weapons/" + weaponId;
        }

        if (quantity > weapon.getStock()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "La quantité demandée dépasse le stock disponible."
            );

            return "redirect:/weapons/" + weaponId;
        }
        try {
            sessionCart.getCart().addWeapon(weapon, quantity);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Le produit a été ajouté au panier."
            );
            return "redirect:/cart";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }
        return "redirect:/weapons/" + weaponId;
    }

    @GetMapping("/cart")
    public String showCart(Model model, Authentication authentication) {
        boolean authenticated = authentication != null && authentication.isAuthenticated();

        BigDecimal originalPrice = sessionCart.getCart().getTotalPrice();
        BigDecimal discountAmount = promotionService.calculateDiscount(originalPrice, authenticated);
        BigDecimal deliveryFee = promotionService.calculateDeliveryFee(originalPrice, authenticated);
        BigDecimal finalPrice = promotionService.calculateFinalPrice(originalPrice, authenticated);
        boolean freeDelivery = promotionService.isFreeDeliveryApplied(originalPrice, authenticated);

        model.addAttribute("title", "Panier");
        model.addAttribute("body", "/WEB-INF/jsp/cart/cart.jsp");
        model.addAttribute("cart", sessionCart.getCart());
        model.addAttribute("originalPrice", originalPrice);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("freeDelivery", freeDelivery);
        model.addAttribute("standardDeliveryFee", new BigDecimal("500.00"));

        return "template/template";
    }

    @PostMapping("/cart/remove/{weaponId}")
    public String removeFromCart(@PathVariable Long weaponId) {

        sessionCart.getCart().removeWeapon(weaponId);

        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{weaponId}")
    public String updateQuantity(
            @PathVariable Long weaponId,
            @RequestParam int quantity) {

        sessionCart.getCart().updateQuantity(weaponId, quantity);

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart() {
        sessionCart.getCart().clear();
        return "redirect:/cart";
    }

}