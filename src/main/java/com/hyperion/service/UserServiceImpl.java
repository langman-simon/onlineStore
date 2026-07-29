package com.hyperion.service;

import com.hyperion.model.User;
import com.hyperion.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user, String rawPassword) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Login already exists");
        }
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    @Override
    public boolean loginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Optional<User> authenticate(String login, String rawPassword) {
        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User updateProfile(String login, String lastName, String firstName,
                              String deliveryAddress, String email, String phone,
                              String secondaryPhone) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setDeliveryAddress(deliveryAddress);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSecondaryPhone(secondaryPhone);

        return userRepository.save(user);
    }

}