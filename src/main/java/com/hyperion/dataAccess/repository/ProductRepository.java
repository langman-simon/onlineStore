package com.hyperion.dataAccess.repository;

import com.hyperion.dataAccess.entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByOrderByNameAsc();

    List<ProductEntity> findByCategoryIdOrderByNameAsc(Long categoryId);

    List<ProductEntity> findByCategoryId(Long categoryId, Sort sort);

    List<ProductEntity> findByNameContainingIgnoreCase(String name, Sort sort);

    List<ProductEntity> findByCategoryIdAndNameContainingIgnoreCase(
            Long categoryId,
            String name,
            Sort sort
    );

    void removeProductById(Long idProduct);

    Optional<ProductEntity> findByReference(String reference);
}
