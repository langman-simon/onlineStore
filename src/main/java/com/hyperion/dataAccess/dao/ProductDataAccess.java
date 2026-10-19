package com.hyperion.dataAccess.dao;

import com.hyperion.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDataAccess {

    List<Product> findAll(String sort);

    List<Product> findByCategory(Long categoryId, String sort);

    List<Product> searchByName(String name, String sort);

    List<Product> searchByNameInCategory(Long categoryId, String name, String sort);

    Optional<Product> findById(Long id);

    Optional<Product> findByReference(String reference);

    Product save(Product product);

    void deleteById(Long id);
}