package com.hyperion.controller;

import com.hyperion.service.CatalogueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalogue")
public class CatalogueController {

    private final CatalogueService catalogueService;

    public CatalogueController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping
    public String catalogue(
            @RequestParam(required = false) Long categoryId,
            Model model
    ) {
        model.addAttribute(
                "weapons",
                catalogueService.findWeapons(categoryId)
        );
        model.addAttribute(
                "categories",
                catalogueService.findAllCategories()
        );
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("titleKey", "page.catalogue");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/catalogue/catalogue.jsp"
        );

        return "template/template";
    }
}
