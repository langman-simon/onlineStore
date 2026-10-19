package com.hyperion.dataAccess.dao;

import com.hyperion.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDataAccess {

    List<Category> findAllOrderByName();

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);
}
