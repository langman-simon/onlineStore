package com.hyperion.dataAccess.dao;

import com.hyperion.dataAccess.repository.CategoryRepository;
import com.hyperion.dataAccess.util.CategoryMapper;
import com.hyperion.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryDAO implements CategoryDataAccess {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryDAO(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Category> findAllOrderByName() {
        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repository.findByName(name).map(mapper::toModel);
    }
}