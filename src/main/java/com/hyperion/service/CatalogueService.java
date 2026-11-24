package com.hyperion.service;

import com.hyperion.model.Category;
import com.hyperion.model.Product;

import java.util.List;
import java.util.Optional;

public interface CatalogueService {

    List<Product> findProducts(Long categoryId);

    List<Product> findProducts(Long categoryId, String search, String sort);

    List<Category> findAllCategories();

    Optional<Product> findProductById(Long productId);

    Optional<Product> findProductByReference(String reference);

    Optional<Category> findCategoryById(Long categoryId);

    Optional<Category> findCategoryByName(String name);

    Product saveProduct(Product product);

    void deleteProduct(Product product);
}