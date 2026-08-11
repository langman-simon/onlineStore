package com.hyperion.controller;

import com.hyperion.model.Weapon;
import com.hyperion.repository.WeaponRepository;
import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/weapons")
public class WeaponController {

    private final WeaponRepository weaponRepository;
    private final GlobalBannerService globalBannerService;

    public WeaponController(
            WeaponRepository weaponRepository,
            GlobalBannerService globalBannerService
    ) {
        this.weaponRepository = weaponRepository;
        this.globalBannerService = globalBannerService;
    }

    @GetMapping("/{id}")
    public String details(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        Optional<Weapon> weaponOpt =
                weaponRepository.findById(id);

        if (weaponOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.product.notFound"
            );

            return "redirect:/catalogue";
        }

        Weapon weapon = weaponOpt.get();

        model.addAttribute("weapon", weapon);
        model.addAttribute("title", weapon.getName());
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/weapon/details.jsp"
        );

        return "template/template";
    }
}
