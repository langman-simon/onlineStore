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

    private static final String DEFAULT_SORT = "nameAsc";

    private final CatalogueService catalogueService;

    public CatalogueController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping
    public String catalogue(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = DEFAULT_SORT) String sort,
            Model model
    ) {
        String searchQuery = search.trim();
        String selectedSort = normalizeSort(sort);

        model.addAttribute(
                "weapons",
                catalogueService.findWeapons(
                        categoryId,
                        searchQuery,
                        selectedSort
                )
        );
        model.addAttribute(
                "categories",
                catalogueService.findAllCategories()
        );
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("selectedSort", selectedSort);
        model.addAttribute("titleKey", "page.catalogue");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/catalogue/catalogue.jsp"
        );

        return "template/template";
    }

    private String normalizeSort(String sort) {
        if (sort == null) {
            return DEFAULT_SORT;
        }

        return switch (sort) {
            case "nameAsc", "nameDesc", "priceAsc", "priceDesc" -> sort;
            default -> DEFAULT_SORT;
        };
    }
}
