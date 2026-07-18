package com.hyperion.controller;

import com.hyperion.model.Weapon;
import com.hyperion.repository.WeaponRepository;
import com.hyperion.session.Panel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    private final Panel panel;
    private final WeaponRepository weaponRepository;

    public CartController(
            Panel panel,
            WeaponRepository weaponRepository
    ) {
        this.panel = panel;
        this.weaponRepository = weaponRepository;
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

        panel.getCart().addWeapon(weapon, quantity);

        redirectAttributes.addFlashAttribute(
                "success",
                "Le produit a été ajouté au panier."
        );

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("title", "Panier");
        model.addAttribute("body", "/WEB-INF/jsp/cart/cart.jsp");
        model.addAttribute("cart", panel.getCart());

        return "template/template";
    }

    @PostMapping("/cart/remove/{weaponId}")
    public String removeFromCart(@PathVariable Long weaponId) {

        panel.getCart().removeWeapon(weaponId);

        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{weaponId}")
    public String updateQuantity(
            @PathVariable Long weaponId,
            @RequestParam int quantity) {

        panel.getCart().updateQuantity(weaponId, quantity);

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart() {
        panel.getCart().clear();
        return "redirect:/cart";
    }

}