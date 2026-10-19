package com.hyperion.dataAccess.util;

import com.hyperion.dataAccess.entity.CategoryEntity;
import com.hyperion.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toModel(CategoryEntity entity);

    CategoryEntity toEntity(Category model);
}