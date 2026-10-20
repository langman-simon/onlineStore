package com.hyperion.dataAccess.dao;

import com.hyperion.dataAccess.entity.PromotionEntity;
import com.hyperion.dataAccess.repository.CategoryRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import com.hyperion.dataAccess.repository.PromotionRepository;
import com.hyperion.dataAccess.util.PromotionMapper;
import com.hyperion.model.Promotion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PromotionDAO implements PromotionDataAccess {

    private final PromotionRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PromotionMapper mapper;

    public PromotionDAO(
            PromotionRepository repository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            PromotionMapper mapper
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Promotion> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<Promotion> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    @Transactional
    public Promotion save(Promotion promotion) {
        PromotionEntity entity = mapper.toEntity(promotion);

        entity.setTargetCategory(
                promotion.getTargetCategory() == null
                        ? null
                        : categoryRepository.getReferenceById(promotion.getTargetCategory().getId())
        );

        entity.setTargetWeapon(
                promotion.getTargetProduct() == null
                        ? null
                        : productRepository.getReferenceById(promotion.getTargetProduct().getId())
        );

        return mapper.toModel(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}