package com.hyperion.service;

import com.hyperion.dataAccess.entity.UserEntity;
import java.util.Optional;

public interface UserService {
    UserEntity register(UserEntity user, String rawPassword);
    boolean loginExists(String login);
    Optional<UserEntity> authenticate(String login, String rawPassword);
    Optional<UserEntity> findByLogin(String login);
    UserEntity updateProfile(String currentLogin, String newLogin, String lastName, String firstName,
                             String deliveryAddress, String email, String phone, String secondaryPhone);
}