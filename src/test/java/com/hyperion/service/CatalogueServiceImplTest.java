package com.hyperion.service;

import com.hyperion.dataAccess.entity.CategoryEntity;
import com.hyperion.dataAccess.entity.ProductEntity;
import com.hyperion.dataAccess.repository.CategoryRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CatalogueServiceImplTest {

    // Tri par défaut appliqué par le service : par nom, ordre croissant
    private static final Sort SORT_BY_NAME_ASC =
            Sort.by(Sort.Direction.ASC, "name");

    private ProductRepository weaponRepository;
    private CategoryRepository categoryRepository;
    private CatalogueServiceImpl catalogueService;

    @BeforeEach
    void setUp() {
        weaponRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        catalogueService = new CatalogueServiceImpl(
                weaponRepository,
                categoryRepository
        );
    }

    @Test
    void shouldFindAllWeaponsWhenCategoryIsNull() {
        // Arrange : sans catégorie ni recherche, le service appelle findAll(tri)
        ProductEntity weapon = new ProductEntity();
        when(weaponRepository.findAll(SORT_BY_NAME_ASC))
                .thenReturn(List.of(weapon));

        // Act
        List<ProductEntity> weapons = catalogueService.findWeapons(null);

        // Assert
        assertThat(weapons).containsExactly(weapon);
        verify(weaponRepository).findAll(SORT_BY_NAME_ASC);
    }

    @Test
    void shouldFindWeaponsByCategory() {
        // Arrange : avec une catégorie, le service appelle findByCategoryId(id, tri)
        ProductEntity weapon = new ProductEntity();
        when(weaponRepository.findByCategoryId(1L, SORT_BY_NAME_ASC))
                .thenReturn(List.of(weapon));

        // Act
        List<ProductEntity> weapons = catalogueService.findWeapons(1L);

        // Assert
        assertThat(weapons).containsExactly(weapon);
        verify(weaponRepository).findByCategoryId(1L, SORT_BY_NAME_ASC);
    }

    @Test
    void shouldFindAllCategories() {
        CategoryEntity category = new CategoryEntity();
        when(categoryRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(category));

        List<CategoryEntity> categories = catalogueService.findAllCategories();

        assertThat(categories).containsExactly(category);
        verify(categoryRepository).findAllByOrderByNameAsc();
    }

    @Test
    void shouldFindWeaponById() {
        ProductEntity weapon = new ProductEntity();
        when(weaponRepository.findById(1L))
                .thenReturn(Optional.of(weapon));

        Optional<ProductEntity> result = catalogueService.findWeaponById(1L);

        assertThat(result).contains(weapon);
    }

    @Test
    void shouldSaveWeapon() {
        ProductEntity weapon = new ProductEntity();
        when(weaponRepository.save(weapon))
                .thenReturn(weapon);

        ProductEntity saved = catalogueService.saveWeapon(weapon);

        assertThat(saved).isSameAs(weapon);
        verify(weaponRepository).save(weapon);
    }

    @Test
    void shouldDeleteWeapon() {
        ProductEntity weapon = new ProductEntity();

        catalogueService.deleteWeapon(weapon);

        verify(weaponRepository).delete(weapon);
    }
}