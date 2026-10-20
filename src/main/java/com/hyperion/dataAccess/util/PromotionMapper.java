package com.hyperion.dataAccess.util;

import com.hyperion.dataAccess.entity.PromotionEntity;
import com.hyperion.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductMapper.class})
public interface PromotionMapper {

    @Mapping(source = "targetWeapon", target = "targetProduct")
    Promotion toModel(PromotionEntity entity);

    @Mapping(source = "targetProduct", target = "targetWeapon")
    PromotionEntity toEntity(Promotion model);
}