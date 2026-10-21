package com.hyperion.controller;

import com.hyperion.service.CatalogueService;
import com.hyperion.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PromotionService promotionService;
    private final CatalogueService catalogueService;

    public HomeController(
            PromotionService promotionService,
            CatalogueService catalogueService
    ) {
        this.promotionService = promotionService;
        this.catalogueService = catalogueService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(
                "promotions",
                promotionService.findAll().stream()
                        .filter(promotion -> promotion.isCurrentlyValid())
                        .toList()
        );

        model.addAttribute(
                "freeDeliveryThreshold",
                promotionService.getFreeDeliveryThreshold()
        );
        model.addAttribute(
                "tier2Threshold",
                promotionService.getTier2Threshold()
        );
        model.addAttribute(
                "tier2Rate",
                promotionService.getTier2RatePercent()
        );
        model.addAttribute(
                "tier3Threshold",
                promotionService.getTier3Threshold()
        );
        model.addAttribute(
                "tier3Rate",
                promotionService.getTier3RatePercent()
        );

        catalogueService.findCategoryByName("category.aeg")
                .ifPresent(category ->
                        model.addAttribute("aegCategory", category)
                );
        catalogueService.findCategoryByName("category.protection")
                .ifPresent(category ->
                        model.addAttribute("protectionCategory", category)
                );
        catalogueService.findCategoryByName("category.sniper")
                .ifPresent(category ->
                        model.addAttribute("sniperCategory", category)
                );

        model.addAttribute("titleKey", "page.home");
        model.addAttribute("body", "/WEB-INF/jsp/home.jsp");
        return "template/template";
    }
}
