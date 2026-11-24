package com.hyperion.dataAccess.util;

import com.hyperion.dataAccess.entity.PromotionEntity;
import com.hyperion.model.Promotion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductMapper.class})
public interface PromotionMapper {

    Promotion toModel(PromotionEntity entity);

    PromotionEntity toEntity(Promotion model);
}