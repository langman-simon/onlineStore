package com.hyperion.controller;

import com.hyperion.model.Product;
import com.hyperion.service.CatalogueService;
import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final CatalogueService catalogueService;
    private final GlobalBannerService globalBannerService;

    public ProductController(
            CatalogueService catalogueService,
            GlobalBannerService globalBannerService
    ) {
        this.catalogueService = catalogueService;
        this.globalBannerService = globalBannerService;
    }

    @GetMapping("/{id}")
    public String details(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        Optional<Product> product = catalogueService.findWeaponById(id);

        if (product.isEmpty()) {
            globalBannerService.error(session, "error.product.notFound");
            return "redirect:/catalogue";
        }

        model.addAttribute("product", product.get());
        model.addAttribute("title", product.get().getName());

        return "product/details";
    }
}