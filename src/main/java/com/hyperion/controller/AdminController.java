package com.hyperion.controller;

import com.hyperion.model.Category;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CategoryRepository;
import com.hyperion.repository.WeaponRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final WeaponRepository weaponRepository;
    private final CategoryRepository categoryRepository;

    public AdminController(
            WeaponRepository weaponRepository,
            CategoryRepository categoryRepository
    ) {
        this.weaponRepository = weaponRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    public String catalogue(
            @RequestParam(required = false) Long categoryId,
            Model model
    ) {
        List<Weapon> weapons;

        if (categoryId == null) {
            weapons = weaponRepository.findByOrderByNameAsc();
        } else {
            weapons = weaponRepository
                    .findByCategoryIdOrderByNameAsc(categoryId);
        }

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("weapons", weapons);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("title", "Catalogue");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/catalogue/catalogue.jsp"
        );

        return "template/template";
    }

}
