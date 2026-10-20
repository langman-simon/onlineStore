package com.hyperion.service;

import com.hyperion.model.Category;
import com.hyperion.model.Product;

import java.util.List;
import java.util.Optional;

public interface CatalogueService {

    List<Product> findWeapons(Long categoryId);

    List<Product> findWeapons(Long categoryId, String search, String sort);

    List<Category> findAllCategories();

    Optional<Product> findWeaponById(Long weaponId);

    Optional<Product> findWeaponByReference(String reference);

    Optional<Category> findCategoryById(Long categoryId);

    Optional<Category> findCategoryByName(String name);

    Product saveWeapon(Product weapon);

    void deleteWeapon(Product weapon);
}