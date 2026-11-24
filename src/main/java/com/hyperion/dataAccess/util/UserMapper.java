package com.hyperion.dataAccess.util;

import com.hyperion.dataAccess.entity.UserEntity;
import com.hyperion.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserEntity entity);

    UserEntity toEntity(User model);
}
