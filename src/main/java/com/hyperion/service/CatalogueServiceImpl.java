package com.hyperion.service;

import com.hyperion.dataAccess.dao.CategoryDataAccess;
import com.hyperion.dataAccess.dao.ProductDataAccess;
import com.hyperion.model.Category;
import com.hyperion.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogueServiceImpl implements CatalogueService {

    private static final String DEFAULT_SORT = "nameAsc";

    private final ProductDataAccess productDataAccess;
    private final CategoryDataAccess categoryDataAccess;

    public CatalogueServiceImpl(
            ProductDataAccess productDataAccess,
            CategoryDataAccess categoryDataAccess
    ) {
        this.productDataAccess = productDataAccess;
        this.categoryDataAccess = categoryDataAccess;
    }

    @Override
    public List<Product> findProducts(Long categoryId) {
        return findProducts(categoryId, "", DEFAULT_SORT);
    }

    @Override
    public List<Product> findProducts(Long categoryId, String search, String sort) {
        String searchQuery = search == null ? "" : search.trim();

        if (categoryId == null && searchQuery.isEmpty()) {
            return productDataAccess.findAll(sort);
        }

        if (categoryId == null) {
            return productDataAccess.searchByName(searchQuery, sort);
        }

        if (searchQuery.isEmpty()) {
            return productDataAccess.findByCategory(categoryId, sort);
        }

        return productDataAccess.searchByNameInCategory(categoryId, searchQuery, sort);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryDataAccess.findAllOrderByName();
    }

    @Override
    public Optional<Product> findProductById(Long productId) {
        return productDataAccess.findById(productId);
    }

    @Override
    public Optional<Product> findProductByReference(String reference) {
        return productDataAccess.findByReference(reference);
    }

    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryDataAccess.findById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryDataAccess.findByName(name);
    }

    @Override
    public Product saveProduct(Product product) {
        return productDataAccess.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productDataAccess.deleteById(product.getId());
    }
}