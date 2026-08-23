package com.hyperion.service;

import com.hyperion.model.Category;
import com.hyperion.model.Weapon;

import java.util.List;
import java.util.Optional;

public interface CatalogueService {

    List<Weapon> findWeapons(Long categoryId);

    List<Weapon> findWeapons(
            Long categoryId,
            String search,
            String sort
    );

    List<Category> findAllCategories();

    Optional<Weapon> findWeaponById(Long weaponId);

    Optional<Weapon> findWeaponByReference(String reference);

    Optional<Category> findCategoryById(Long categoryId);

    Optional<Category> findCategoryByName(String name);

    Weapon saveWeapon(Weapon weapon);

    void deleteWeapon(Weapon weapon);
}
