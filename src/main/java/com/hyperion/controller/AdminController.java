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
import java.util.Optional;

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
        model.addAttribute(
                "title",
                "Administration du catalogue"
        );
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/admin/admin.jsp"
        );

        return "template/template";
    }

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
                    manufacturer
            );

            validateRequiredImage(image);

            String normalizedReference = reference.trim();

            if (weaponRepository
                    .findByReference(normalizedReference)
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "Une arme possède déjà la référence "
                                + normalizedReference + "."
                );
            }

            Category category = findCategory(categoryId);

            String imageUrl =
                    imageStorageService.saveWeaponImage(image);

            Weapon weapon = new Weapon();

            weapon.setName(name.trim());
            weapon.setDescription(description.trim());
            weapon.setPrice(price);
            weapon.setStock(stock);
            weapon.setReference(normalizedReference);
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
                    "Une erreur est survenue pendant "
                            + "l’ajout du produit."
            );
        }

        return "redirect:/admin";
    }

    @PostMapping("/weapons/update/{weaponId}")
    public String updateWeapon(
            @PathVariable Long weaponId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam String reference,
            @RequestParam String manufacturer,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        try {
            validateWeaponData(
                    name,
                    description,
                    price,
                    stock,
                    reference,
                    manufacturer
            );

            Weapon weapon = findWeapon(weaponId);
            Category category = findCategory(categoryId);

            String normalizedReference = reference.trim();

            Optional<Weapon> weaponWithSameReference =
                    weaponRepository.findByReference(
                            normalizedReference
                    );

            if (weaponWithSameReference.isPresent()
                    && !weaponWithSameReference
                    .get()
                    .getId()
                    .equals(weaponId)) {

                throw new IllegalArgumentException(
                        "Une autre arme possède déjà la référence "
                                + normalizedReference + "."
                );
            }

            weapon.setName(name.trim());
            weapon.setDescription(description.trim());
            weapon.setPrice(price);
            weapon.setStock(stock);
            weapon.setReference(normalizedReference);
            weapon.setManufacturer(manufacturer.trim());
            weapon.setCategory(category);

            if (image != null && !image.isEmpty()) {
                String imageUrl =
                        imageStorageService.saveWeaponImage(image);

                weapon.setImageUrl(imageUrl);
            }

            weaponRepository.save(weapon);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Le produit \"" + weapon.getName()
                            + "\" a été modifié."
            );

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Une erreur est survenue pendant "
                            + "la modification du produit."
            );
        }

        return "redirect:/admin";
    }

    @PostMapping("/weapons/stock/{weaponId}")
    public String updateStock(
            @PathVariable Long weaponId,
            @RequestParam int stock,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (stock < 0) {
                throw new IllegalArgumentException(
                        "Le stock ne peut pas être négatif."
                );
            }

            Weapon weapon = findWeapon(weaponId);

            weapon.setStock(stock);
            weaponRepository.save(weapon);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Le stock du produit \""
                            + weapon.getName()
                            + "\" a été mis à jour."
            );

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );

        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Une erreur est survenue pendant "
                            + "la modification du stock."
            );
        }

        return "redirect:/admin";
    }

    @PostMapping("/remove/{weaponId}")
    public String removeWeapon(
            @PathVariable Long weaponId,
            RedirectAttributes redirectAttributes
    ) {
        Weapon weapon = weaponRepository
                .findById(weaponId)
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
                            + "Il est peut-être utilisé "
                            + "dans une commande."
            );
        }

        return "redirect:/admin";
    }

    private Weapon findWeapon(Long weaponId) {
        return weaponRepository.findById(weaponId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Le produit demandé n’existe pas."
                        )
                );
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La catégorie sélectionnée "
                                        + "n’existe pas."
                        )
                );
    }

    private void validateWeaponData(
            String name,
            String description,
            BigDecimal price,
            int stock,
            String reference,
            String manufacturer
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom du produit est obligatoire."
            );
        }

        if (name.trim().length() > 150) {
            throw new IllegalArgumentException(
                    "Le nom du produit ne peut pas dépasser "
                            + "150 caractères."
            );
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "La description est obligatoire."
            );
        }

        if (description.trim().length() > 1000) {
            throw new IllegalArgumentException(
                    "La description ne peut pas dépasser "
                            + "1000 caractères."
            );
        }

        if (price == null
                || price.compareTo(BigDecimal.ZERO) < 0) {

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

        if (reference.trim().length() > 100) {
            throw new IllegalArgumentException(
                    "La référence ne peut pas dépasser "
                            + "100 caractères."
            );
        }

        if (manufacturer == null || manufacturer.isBlank()) {
            throw new IllegalArgumentException(
                    "Le fabricant est obligatoire."
            );
        }

        if (manufacturer.trim().length() > 100) {
            throw new IllegalArgumentException(
                    "Le fabricant ne peut pas dépasser "
                            + "100 caractères."
            );
        }
    }

    private void validateRequiredImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException(
                    "Une image est obligatoire."
            );
        }
    }
}