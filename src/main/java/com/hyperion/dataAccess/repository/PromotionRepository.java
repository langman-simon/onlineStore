package com.hyperion.dataAccess.repository;

import com.hyperion.dataAccess.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
}