package com.hyperion.service;

import com.hyperion.dataAccess.entity.CategoryEntity;
import com.hyperion.dataAccess.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface CatalogueService {

    List<ProductEntity> findWeapons(Long categoryId);

    List<ProductEntity> findWeapons(
            Long categoryId,
            String search,
            String sort
    );

    List<CategoryEntity> findAllCategories();

    Optional<ProductEntity> findWeaponById(Long weaponId);

    Optional<ProductEntity> findWeaponByReference(String reference);

    Optional<CategoryEntity> findCategoryById(Long categoryId);

    Optional<CategoryEntity> findCategoryByName(String name);

    ProductEntity saveWeapon(ProductEntity weapon);

    void deleteWeapon(ProductEntity weapon);
}
