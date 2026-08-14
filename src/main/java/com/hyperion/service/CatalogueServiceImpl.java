package com.hyperion.service;

import com.hyperion.model.Category;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CategoryRepository;
import com.hyperion.repository.WeaponRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogueServiceImpl implements CatalogueService {

    private final WeaponRepository weaponRepository;
    private final CategoryRepository categoryRepository;

    public CatalogueServiceImpl(
            WeaponRepository weaponRepository,
            CategoryRepository categoryRepository
    ) {
        this.weaponRepository = weaponRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Weapon> findWeapons(Long categoryId) {
        if (categoryId == null) {
            return weaponRepository.findByOrderByNameAsc();
        }

        return weaponRepository.findByCategoryIdOrderByNameAsc(categoryId);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Optional<Weapon> findWeaponById(Long weaponId) {
        return weaponRepository.findById(weaponId);
    }

    @Override
    public Optional<Weapon> findWeaponByReference(String reference) {
        return weaponRepository.findByReference(reference);
    }

    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Weapon saveWeapon(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    @Override
    public void deleteWeapon(Weapon weapon) {
        weaponRepository.delete(weapon);
    }
}
