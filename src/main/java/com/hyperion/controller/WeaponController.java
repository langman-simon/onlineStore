package com.hyperion.controller;

import com.hyperion.model.Weapon;
import com.hyperion.repository.WeaponRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/weapons")
public class WeaponController {

    private final WeaponRepository weaponRepository;

    public WeaponController(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {

        Weapon weapon = weaponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

        model.addAttribute("weapon", weapon);
        model.addAttribute("title", weapon.getName());
        model.addAttribute("body", "/WEB-INF/jsp/weapon/details.jsp");

        return "template/template";
    }

}