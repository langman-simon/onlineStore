package com.hyperion.controller;

import com.hyperion.model.Category;
import com.hyperion.model.Promotion;
import com.hyperion.model.Weapon;
import com.hyperion.service.CatalogueService;
import com.hyperion.service.GlobalBannerService;
import com.hyperion.service.ImageStorageService;
import com.hyperion.service.PromotionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CatalogueService catalogueService;
    private final ImageStorageService imageStorageService;
    private final PromotionService promotionService;
    private final GlobalBannerService globalBannerService;

    public AdminController(
            CatalogueService catalogueService,
            ImageStorageService imageStorageService,
            PromotionService promotionService,
            GlobalBannerService globalBannerService
    ) {
        this.catalogueService = catalogueService;
        this.imageStorageService = imageStorageService;
        this.promotionService = promotionService;
        this.globalBannerService = globalBannerService;
    }

    @GetMapping
    public String catalogue(
            @RequestParam(required = false) Long categoryId,
            Model model
    ) {
        List<Weapon> weapons = catalogueService.findWeapons(categoryId);
        List<Category> categories = catalogueService.findAllCategories();

        model.addAttribute("weapons", weapons);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("promotions", promotionService.findAll());

        model.addAttribute(
                "titleKey",
                "page.admin"
        );

        model.addAttribute(
                "body",
                "/WEB-INF/jsp/admin/admin.jsp"
        );

        return "template/template";
    }

    // =========================================================
    // WEAPONS
    // =========================================================

    @PostMapping("/weapons/add")
    public String addWeapon(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam String reference,
            @RequestParam String manufacturer,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image,
            HttpSession session
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

            String normalizedReference = reference.trim();

            if (catalogueService
                    .findWeaponByReference(normalizedReference)
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "error.admin.referenceExists"
                );
            }

            Category category = findCategory(categoryId);

            String imageUrl =
                    image == null || image.isEmpty()
                            ? null
                            : imageStorageService.saveWeaponImage(image);

            Weapon weapon = new Weapon();

            weapon.setName(name.trim());
            weapon.setDescription(description.trim());
            weapon.setPrice(price);
            weapon.setStock(stock);
            weapon.setReference(normalizedReference);
            weapon.setManufacturer(manufacturer.trim());
            weapon.setCategory(category);
            weapon.setImageUrl(imageUrl);

            catalogueService.saveWeapon(weapon);

            globalBannerService.success(
                    session,
                    "message.admin.weaponAdded",
                    weapon.getName()
            );

        } catch (IllegalArgumentException | IllegalStateException exception) {

            globalBannerService.error(
                    session,
                    exception.getMessage()
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
            HttpSession session
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
                    catalogueService.findWeaponByReference(
                            normalizedReference
                    );

            if (weaponWithSameReference.isPresent()
                    && !weaponWithSameReference
                    .get()
                    .getId()
                    .equals(weaponId)) {

                globalBannerService.error(session, "error.admin.referenceOther");
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

            catalogueService.saveWeapon(weapon);

            globalBannerService.success(
                    session,
                    "message.admin.weaponUpdated",
                    weapon.getName()
            );

        } catch (IllegalArgumentException | IllegalStateException exception) {

            globalBannerService.error(
                    session,
                    exception.getMessage()
            );

        }

        return "redirect:/admin";
    }

    @PostMapping("/remove/{weaponId}")
    public String removeWeapon(
            @PathVariable Long weaponId,
            HttpSession session
    ) {
        Weapon weapon = catalogueService
                .findWeaponById(weaponId)
                .orElse(null);

        if (weapon == null) {

            globalBannerService.error(
                    session,
                    "error.admin.weaponNotFound"
            );

            return "redirect:/admin";
        }

        try {
            catalogueService.deleteWeapon(weapon);

            globalBannerService.success(
                    session,
                    "message.admin.weaponDeleted",
                    weapon.getName()
            );

        } catch (DataIntegrityViolationException exception) {

            globalBannerService.error(
                    session,
                    "error.admin.weaponDelete"
            );
        }

        return "redirect:/admin";
    }

    // =========================================================
    // PROMOTIONS
    // =========================================================

    @PostMapping("/promotions/add")
    public String addPromotion(
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false)
            String discountPercentage,
            @RequestParam(
                    required = false,
                    defaultValue = "false"
            )
            boolean freeDelivery,
            @RequestParam(required = false)
            String startDate,
            @RequestParam(required = false)
            String endDate,
            @RequestParam(required = false) Long targetCategoryId,
            @RequestParam(required = false) Long targetWeaponId,
            @RequestParam(required = false, defaultValue = "false") boolean active,
            HttpSession session
    ) {
        try {
            BigDecimal discount =
                    parseDiscount(discountPercentage);

            validatePromotionData(
                    title,
                    discount
            );

            if (targetCategoryId != null && targetWeaponId != null) {
                globalBannerService.error(session, "message.admin.noBothChoice");
            }

            Category targetCategory = targetCategoryId != null ? findCategory(targetCategoryId) : null;
            Weapon targetWeapon = targetWeaponId != null ? findWeapon(targetWeaponId) : null;

            LocalDate parsedStartDate =
                    parseDate(startDate);

            LocalDate parsedEndDate =
                    parseDate(endDate);

            validatePromotionDates(
                    parsedStartDate,
                    parsedEndDate
            );

            Promotion promotion = new Promotion();

            promotion.setTitle(title.trim());

            promotion.setDescription(
                    normalizeOptionalText(description)
            );

            promotion.setDiscountPercentage(discount);
            promotion.setFreeDelivery(freeDelivery);
            promotion.setStartDate(parsedStartDate);
            promotion.setEndDate(parsedEndDate);
            promotion.setActive(active);
            promotion.setTargetCategory(targetCategory);
            promotion.setTargetWeapon(targetWeapon);

            promotionService.save(promotion);

            globalBannerService.success(
                    session,
                    "message.admin.promotionAdded",
                    promotion.getTitle()
            );

        } catch (IllegalArgumentException exception) {

            globalBannerService.error(
                    session,
                    exception.getMessage()
            );

        }

        return "redirect:/admin";
    }

    @PostMapping("/promotions/update/{promotionId}")
    public String updatePromotion(
            @PathVariable Long promotionId,
            @RequestParam String title,
            @RequestParam(required = false)
            String description,
            @RequestParam(required = false)
            String discountPercentage,
            @RequestParam(
                    required = false,
                    defaultValue = "false"
            )
            boolean freeDelivery,
            @RequestParam(required = false)
            String startDate,
            @RequestParam(required = false)
            String endDate,
            @RequestParam(required = false) Long targetCategoryId,
            @RequestParam(required = false) Long targetWeaponId,
            @RequestParam(required = false, defaultValue = "false") boolean active,
            HttpSession session
    ) {
        try {
            BigDecimal discount =
                    parseDiscount(discountPercentage);

            validatePromotionData(
                    title,
                    discount
            );

            if (targetCategoryId != null && targetWeaponId != null) {
                globalBannerService.error(session, "Choisissez soit une catégorie, soit un produit, pas les deux.");
            }

            Category targetCategory = targetCategoryId != null ? findCategory(targetCategoryId) : null;
            Weapon targetWeapon = targetWeaponId != null ? findWeapon(targetWeaponId) : null;

            LocalDate parsedStartDate =
                    parseDate(startDate);

            LocalDate parsedEndDate =
                    parseDate(endDate);

            validatePromotionDates(
                    parsedStartDate,
                    parsedEndDate
            );

            Promotion promotion =
                    findPromotion(promotionId);

            promotion.setTitle(title.trim());

            promotion.setDescription(
                    normalizeOptionalText(description)
            );

            promotion.setDiscountPercentage(discount);
            promotion.setFreeDelivery(freeDelivery);
            promotion.setStartDate(parsedStartDate);
            promotion.setEndDate(parsedEndDate);
            promotion.setActive(active);
            promotion.setTargetCategory(targetCategory);
            promotion.setTargetWeapon(targetWeapon);

            promotionService.save(promotion);

            globalBannerService.success(
                    session,
                    "message.admin.promotionUpdated",
                    promotion.getTitle()
            );

        } catch (IllegalArgumentException exception) {

            globalBannerService.error(
                    session,
                    exception.getMessage()
            );

        }

        return "redirect:/admin";
    }

    @PostMapping("/promotions/remove/{promotionId}")
    public String removePromotion(
            @PathVariable Long promotionId,
            HttpSession session
    ) {
        Optional<Promotion> promotion =
                promotionService.findById(promotionId);

        if (promotion.isEmpty()) {

            globalBannerService.error(
                    session,
                    "error.admin.promotionNotFound"
            );

            return "redirect:/admin";
        }

        try {
            promotionService.deleteById(promotionId);

            globalBannerService.success(
                    session,
                    "message.admin.promotionDeleted",
                    promotion.get().getTitle()
            );

        } catch (DataIntegrityViolationException exception) {

            globalBannerService.error(
                    session,
                    "error.admin.promotionDelete"
            );
        }

        return "redirect:/admin";
    }

    // =========================================================
    // PRIVATE METHODS
    // =========================================================

    private Weapon findWeapon(Long weaponId) {
        return catalogueService
                .findWeaponById(weaponId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "error.admin.weaponNotFound"
                        )
                );
    }

    private Category findCategory(Long categoryId) {
        return catalogueService
                .findCategoryById(categoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "error.admin.categoryNotFound"
                        )
                );
    }

    private Promotion findPromotion(Long promotionId) {
        return promotionService
                .findById(promotionId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "error.admin.promotionNotFound"
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
                    "error.admin.nameRequired"
            );
        }

        if (name.trim().length() > 150) {

            throw new IllegalArgumentException(
                    "error.admin.nameLength"
            );
        }

        if (description == null || description.isBlank()) {

            throw new IllegalArgumentException(
                    "error.admin.descriptionRequired"
            );
        }

        if (description.trim().length() > 1000) {

            throw new IllegalArgumentException(
                    "error.admin.descriptionLength"
            );
        }

        if (price == null) {
            throw new IllegalArgumentException(
                    "error.admin.priceRequired"
            );
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "error.admin.priceNegative"
            );
        }

        if (stock < 0) {

            throw new IllegalArgumentException(
                    "error.admin.stockNegative"
            );
        }

        if (reference == null || reference.isBlank()) {

            throw new IllegalArgumentException(
                    "error.admin.referenceRequired"
            );
        }

        if (reference.trim().length() > 100) {

            throw new IllegalArgumentException(
                    "error.admin.referenceLength"
            );
        }

        if (manufacturer == null
                || manufacturer.isBlank()) {

            throw new IllegalArgumentException(
                    "error.admin.manufacturerRequired"
            );
        }

        if (manufacturer.trim().length() > 100) {

            throw new IllegalArgumentException(
                    "error.admin.manufacturerLength"
            );
        }
    }

    private void validatePromotionData(
            String title,
            BigDecimal discountPercentage
    ) {
        if (title == null || title.isBlank()) {

            throw new IllegalArgumentException(
                    "error.admin.promotionTitleRequired"
            );
        }

        if (title.trim().length() > 150) {

            throw new IllegalArgumentException(
                    "error.admin.promotionTitleLength"
            );
        }

        if (discountPercentage != null
                && (
                discountPercentage.compareTo(
                        BigDecimal.ZERO
                ) < 0
                        || discountPercentage.compareTo(
                        new BigDecimal("100")
                ) > 0
        )) {

            throw new IllegalArgumentException(
                    "error.admin.discountRange"
            );
        }
    }

    private BigDecimal parseDiscount(
            String discountPercentage
    ) {
        if (discountPercentage == null
                || discountPercentage.isBlank()) {

            return null;
        }

        try {
            return new BigDecimal(
                    discountPercentage
            );

        } catch (NumberFormatException exception) {

            throw new IllegalArgumentException(
                    "error.admin.discountInvalid"
            );
        }
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(date);

        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "error.admin.dateInvalid"
            );
        }
    }

    private void validatePromotionDates(
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate != null
                && endDate != null
                && startDate.isAfter(endDate)) {

            throw new IllegalArgumentException(
                    "error.admin.dateOrder"
            );
        }
    }

    private String normalizeOptionalText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
