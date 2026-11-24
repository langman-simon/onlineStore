package com.hyperion.dataAccess.dao;

import com.hyperion.model.User;

import java.util.Optional;

public interface UserDataAccess {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    User save(User user);
}
