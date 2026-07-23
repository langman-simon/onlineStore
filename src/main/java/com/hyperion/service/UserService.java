package com.hyperion.service;

import com.hyperion.model.User;

public interface UserService {
    User inscrire(User user, String motDePasseClair);
    boolean loginExiste(String login);
}
