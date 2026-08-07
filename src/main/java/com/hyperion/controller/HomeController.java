package com.hyperion.controller;

import com.hyperion.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PromotionService promotionService;

    public HomeController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Accueil");
        model.addAttribute("promotions", promotionService.findAll().stream()
                .filter(p -> p.isCurrentlyValid())
                .toList());

        model.addAttribute("freeDeliveryThreshold", promotionService.getFreeDeliveryThreshold());
        model.addAttribute("tier2Threshold", promotionService.getTier2Threshold());
        model.addAttribute("tier2Rate", promotionService.getTier2RatePercent());
        model.addAttribute("tier3Threshold", promotionService.getTier3Threshold());
        model.addAttribute("tier3Rate", promotionService.getTier3RatePercent());

        model.addAttribute("body", "/WEB-INF/jsp/home.jsp");
        return "template/template";
    }
}