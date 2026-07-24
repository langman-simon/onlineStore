package com.hyperion.controller;

import com.hyperion.model.Category;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CategoryRepository;
import com.hyperion.repository.WeaponRepository;
import com.hyperion.service.ImageStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final WeaponRepository weaponRepository;
    private final CategoryRepository categoryRepository;
    private final ImageStorageService imageStorageService;

    public AdminController(
            WeaponRepository weaponRepository,
            CategoryRepository categoryRepository,
            ImageStorageService imageStorageService
    ) {
        this.weaponRepository = weaponRepository;
        this.categoryRepository = categoryRepository;
        this.imageStorageService = imageStorageService;
    }

    /*
     * Affiche le catalogue administrateur.
     */
    @GetMapping
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
        model.addAttribute("title", "Administration du catalogue");

        model.addAttribute(
                "body",
                "/WEB-INF/jsp/admin/admin.jsp"
        );

        return "template/template";
    }

    /*
     * Ajoute une nouvelle arme au catalogue.
     */
    @PostMapping("/weapons/add")
    public String addWeapon(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam String reference,
            @RequestParam String manufacturer,
            @RequestParam Long categoryId,
            @RequestParam MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        try {
            validateWeaponData(
                    name,
                    description,
                    price,
                    stock,
                    reference,
                    manufacturer,
                    image
            );

            if (weaponRepository.findByReference(reference).isPresent()) {
                redirectAttributes.addFlashAttribute(
                        "error",
                        "Une arme possède déjà la référence " + reference + "."
                );

                return "redirect:/admin";
            }

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "La catégorie sélectionnée n’existe pas."
                            )
                    );

            String imageUrl =
                    imageStorageService.saveWeaponImage(image);

            Weapon weapon = new Weapon();

            weapon.setName(name.trim());
            weapon.setDescription(description.trim());
            weapon.setPrice(price);
            weapon.setStock(stock);
            weapon.setReference(reference.trim());
            weapon.setManufacturer(manufacturer.trim());
            weapon.setCategory(category);
            weapon.setImageUrl(imageUrl);

            weaponRepository.save(weapon);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Le produit \"" + weapon.getName()
                            + "\" a été ajouté au catalogue."
            );

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Une erreur est survenue pendant l’ajout du produit."
            );
        }

        return "redirect:/admin";
    }

    /*
     * Supprime une arme du catalogue.
     */
    @PostMapping("/remove/{weaponId}")
    public String removeWeapon(
            @PathVariable Long weaponId,
            RedirectAttributes redirectAttributes
    ) {
        Weapon weapon = weaponRepository.findById(weaponId)
                .orElse(null);

        if (weapon == null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Le produit demandé n’existe pas."
            );

            return "redirect:/admin";
        }

        try {
            weaponRepository.delete(weapon);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Le produit \"" + weapon.getName()
                            + "\" a été supprimé."
            );

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Impossible de supprimer ce produit. "
                            + "Il est peut-être utilisé dans une commande."
            );
        }

        return "redirect:/admin";
    }

    /*
     * Vérifie les données reçues depuis le formulaire.
     */
    private void validateWeaponData(
            String name,
            String description,
            BigDecimal price,
            int stock,
            String reference,
            String manufacturer,
            MultipartFile image
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom du produit est obligatoire."
            );
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "La description est obligatoire."
            );
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Le prix ne peut pas être négatif."
            );
        }

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "Le stock ne peut pas être négatif."
            );
        }

        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException(
                    "La référence est obligatoire."
            );
        }

        if (manufacturer == null || manufacturer.isBlank()) {
            throw new IllegalArgumentException(
                    "Le fabricant est obligatoire."
            );
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException(
                    "Une image est obligatoire."
            );
        }
    }
}