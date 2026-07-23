package com.hyperion.service;

import com.hyperion.model.User;
import java.util.Optional;

public interface UserService {
    User register(User user, String rawPassword);
    boolean loginExists(String login);
    Optional<User> authenticate(String login, String rawPassword);
}