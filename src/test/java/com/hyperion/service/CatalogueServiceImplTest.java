package com.hyperion.service;

import com.hyperion.model.Category;
import com.hyperion.model.Weapon;
import com.hyperion.repository.CategoryRepository;
import com.hyperion.repository.WeaponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CatalogueServiceImplTest {

    private WeaponRepository weaponRepository;
    private CategoryRepository categoryRepository;
    private CatalogueServiceImpl catalogueService;

    @BeforeEach
    void setUp() {
        weaponRepository = mock(WeaponRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        catalogueService = new CatalogueServiceImpl(
                weaponRepository,
                categoryRepository
        );
    }

    @Test
    void shouldFindAllWeaponsWhenCategoryIsNull() {
        Weapon weapon = new Weapon();

        when(weaponRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(weapon));

        List<Weapon> weapons = catalogueService.findWeapons(null);

        assertThat(weapons).containsExactly(weapon);

        verify(weaponRepository)
                .findAll(any(Sort.class));
    }

    @Test
    void shouldFindWeaponsByCategory() {
        Weapon weapon = new Weapon();

        when(weaponRepository.findByCategoryId(
                eq(1L),
                any(Sort.class)
        )).thenReturn(List.of(weapon));

        List<Weapon> weapons = catalogueService.findWeapons(1L);

        assertThat(weapons).containsExactly(weapon);

        verify(weaponRepository).findByCategoryId(
                eq(1L),
                any(Sort.class)
        );
    }

    @Test
    void shouldFindAllCategories() {
        Category category = new Category();

        when(categoryRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(category));

        List<Category> categories =
                catalogueService.findAllCategories();

        assertThat(categories)
                .containsExactly(category);

        verify(categoryRepository)
                .findAllByOrderByNameAsc();
    }

    @Test
    void shouldFindWeaponById() {
        Weapon weapon = new Weapon();

        when(weaponRepository.findById(1L))
                .thenReturn(Optional.of(weapon));

        Optional<Weapon> result =
                catalogueService.findWeaponById(1L);

        assertThat(result).contains(weapon);
    }

    @Test
    void shouldSaveWeapon() {
        Weapon weapon = new Weapon();

        when(weaponRepository.save(weapon))
                .thenReturn(weapon);

        Weapon saved =
                catalogueService.saveWeapon(weapon);

        assertThat(saved).isSameAs(weapon);

        verify(weaponRepository)
                .save(weapon);
    }

    @Test
    void shouldDeleteWeapon() {
        Weapon weapon = new Weapon();

        catalogueService.deleteWeapon(weapon);

        verify(weaponRepository)
                .delete(weapon);
    }
}
