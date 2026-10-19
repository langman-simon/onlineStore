package com.hyperion.dataAccess.dao;

import com.hyperion.dataAccess.entity.ProductEntity;
import com.hyperion.dataAccess.repository.CategoryRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import com.hyperion.dataAccess.util.ProductMapper;
import com.hyperion.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductDAO implements ProductDataAccess {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductDAO(
            ProductRepository repository,
            CategoryRepository categoryRepository,
            ProductMapper mapper
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> findAll(String sort) {
        return toModels(repository.findAll(resolveSort(sort)));
    }

    @Override
    public List<Product> findByCategory(Long categoryId, String sort) {
        return toModels(repository.findByCategoryId(categoryId, resolveSort(sort)));
    }

    @Override
    public List<Product> searchByName(String name, String sort) {
        return toModels(repository.findByNameContainingIgnoreCase(name, resolveSort(sort)));
    }

    @Override
    public List<Product> searchByNameInCategory(Long categoryId, String name, String sort) {
        return toModels(
                repository.findByCategoryIdAndNameContainingIgnoreCase(
                        categoryId,
                        name,
                        resolveSort(sort)
                )
        );
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<Product> findByReference(String reference) {
        return repository.findByReference(reference).map(mapper::toModel);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        entity.setCategory(
                categoryRepository.getReferenceById(product.getCategory().getId())
        );
        return mapper.toModel(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private List<Product> toModels(List<ProductEntity> entities) {
        return entities.stream().map(mapper::toModel).toList();
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
}