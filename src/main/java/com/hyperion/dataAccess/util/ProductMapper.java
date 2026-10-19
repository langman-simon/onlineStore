package com.hyperion.dataAccess.util;

import com.hyperion.dataAccess.entity.ProductEntity;
import com.hyperion.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {

    Product toModel(ProductEntity entity);

    ProductEntity toEntity(Product model);
}