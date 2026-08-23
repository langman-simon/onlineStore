package com.hyperion.service;

import com.hyperion.model.Category;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CategoryRepository;
import com.hyperion.repository.WeaponRepository;
import org.springframework.data.domain.Sort;
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
        return findWeapons(categoryId, "", "nameAsc");
    }

    @Override
    public List<Weapon> findWeapons(
            Long categoryId,
            String search,
            String sort
    ) {
        String searchQuery = search == null ? "" : search.trim();
        Sort weaponSort = resolveSort(sort);

        if (categoryId == null && searchQuery.isEmpty()) {
            return weaponRepository.findAll(weaponSort);
        }

        if (categoryId == null) {
            return weaponRepository.findByNameContainingIgnoreCase(
                    searchQuery,
                    weaponSort
            );
        }

        if (searchQuery.isEmpty()) {
            return weaponRepository.findByCategoryId(
                    categoryId,
                    weaponSort
            );
        }

        return weaponRepository.findByCategoryIdAndNameContainingIgnoreCase(
                categoryId,
                searchQuery,
                weaponSort
        );
    }

    private Sort resolveSort(String sort) {
        if (sort == null) {
            return Sort.by(Sort.Direction.ASC, "name");
        }

        return switch (sort) {
            case "nameDesc" -> Sort.by(Sort.Direction.DESC, "name");
            case "priceAsc" -> Sort.by(Sort.Direction.ASC, "price")
                    .and(Sort.by(Sort.Direction.ASC, "name"));
            case "priceDesc" -> Sort.by(Sort.Direction.DESC, "price")
                    .and(Sort.by(Sort.Direction.ASC, "name"));
            default -> Sort.by(Sort.Direction.ASC, "name");
        };
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
