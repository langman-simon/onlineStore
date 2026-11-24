package com.hyperion.dataAccess.util;

import com.hyperion.dataAccess.entity.CustomerOrderEntity;
import com.hyperion.dataAccess.entity.OrderItemEntity;
import com.hyperion.model.Order;
import com.hyperion.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    Order toModel(CustomerOrderEntity entity);

    List<Order> toModels(List<CustomerOrderEntity> entities);

    OrderItem toItemModel(OrderItemEntity entity);
}
