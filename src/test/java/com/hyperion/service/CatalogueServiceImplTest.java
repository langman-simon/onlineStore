package com.hyperion.service;

import com.hyperion.dataAccess.dao.CategoryDataAccess;
import com.hyperion.dataAccess.dao.ProductDataAccess;
import com.hyperion.model.Category;
import com.hyperion.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CatalogueServiceImplTest {

    private ProductDataAccess productDataAccess;
    private CategoryDataAccess categoryDataAccess;
    private CatalogueServiceImpl catalogueService;

    @BeforeEach
    void setUp() {
        productDataAccess = mock(ProductDataAccess.class);
        categoryDataAccess = mock(CategoryDataAccess.class);
        catalogueService = new CatalogueServiceImpl(productDataAccess, categoryDataAccess);
    }

    @Test
    void shouldFindAllProductsWhenNoCategoryAndNoSearch() {
        Product product = new Product();
        when(productDataAccess.findAll("nameAsc")).thenReturn(List.of(product));

        List<Product> products = catalogueService.findWeapons(null);

        assertThat(products).containsExactly(product);
        verify(productDataAccess).findAll("nameAsc");
    }

    @Test
    void shouldFindProductsByCategory() {
        Product product = new Product();
        when(productDataAccess.findByCategory(1L, "nameAsc")).thenReturn(List.of(product));

        List<Product> products = catalogueService.findWeapons(1L);

        assertThat(products).containsExactly(product);
        verify(productDataAccess).findByCategory(1L, "nameAsc");
    }

    @Test
    void shouldSearchByTrimmedNameWhenNoCategory() {
        Product product = new Product();
        when(productDataAccess.searchByName("m4", "priceAsc")).thenReturn(List.of(product));

        List<Product> products = catalogueService.findWeapons(null, "  m4  ", "priceAsc");

        assertThat(products).containsExactly(product);
        verify(productDataAccess).searchByName("m4", "priceAsc");
    }

    @Test
    void shouldSearchByNameInsideCategory() {
        Product product = new Product();
        when(productDataAccess.searchByNameInCategory(2L, "m4", "nameDesc")).thenReturn(List.of(product));

        List<Product> products = catalogueService.findWeapons(2L, "m4", "nameDesc");

        assertThat(products).containsExactly(product);
        verify(productDataAccess).searchByNameInCategory(2L, "m4", "nameDesc");
    }

    @Test
    void shouldFindAllCategories() {
        Category category = new Category();
        when(categoryDataAccess.findAllOrderByName()).thenReturn(List.of(category));

        List<Category> categories = catalogueService.findAllCategories();

        assertThat(categories).containsExactly(category);
    }

    @Test
    void shouldFindProductById() {
        Product product = new Product();
        when(productDataAccess.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = catalogueService.findWeaponById(1L);

        assertThat(result).contains(product);
    }

    @Test
    void shouldSaveProduct() {
        Product product = new Product();
        when(productDataAccess.save(product)).thenReturn(product);

        Product saved = catalogueService.saveWeapon(product);

        assertThat(saved).isSameAs(product);
        verify(productDataAccess).save(product);
    }

    @Test
    void shouldDeleteProductById() {
        Product product = new Product();
        product.setId(5L);

        catalogueService.deleteWeapon(product);

        verify(productDataAccess).deleteById(5L);
    }
}