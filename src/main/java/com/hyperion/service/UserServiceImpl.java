package com.hyperion.service;

import com.hyperion.dataAccess.dao.UserDataAccess;
import com.hyperion.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDataAccess userDataAccess;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDataAccess userDataAccess, PasswordEncoder passwordEncoder) {
        this.userDataAccess = userDataAccess;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user, String rawPassword) {
        if (userDataAccess.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("error.login.used");
        }
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userDataAccess.save(user);
    }

    @Override
    public boolean loginExists(String login) {
        return userDataAccess.existsByLogin(login);
    }

    @Override
    public Optional<User> authenticate(String login, String rawPassword) {
        Optional<User> userOpt = userDataAccess.findByLogin(login);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDataAccess.findByLogin(login);
    }

    @Override
    public User updateProfile(String currentLogin, String newLogin, String lastName, String firstName,
                              String deliveryAddress, String email, String phone, String secondaryPhone) {
        User user = userDataAccess.findByLogin(currentLogin)
                .orElseThrow(() -> new IllegalArgumentException("error.user.notFound"));

        if (!currentLogin.equals(newLogin) && userDataAccess.existsByLogin(newLogin)) {
            throw new IllegalArgumentException("error.login.used");
        }

        user.setLogin(newLogin);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setDeliveryAddress(deliveryAddress);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSecondaryPhone(secondaryPhone);

        return userDataAccess.save(user);
    }

}
