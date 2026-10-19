package com.hyperion.service;

import com.hyperion.dataAccess.entity.UserEntity;
import com.hyperion.dataAccess.repository.UserRepository;
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
    public UserEntity register(UserEntity user, String rawPassword) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("error.login.used");
        }
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    @Override
    public boolean loginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Optional<UserEntity> authenticate(String login, String rawPassword) {
        Optional<UserEntity> userOpt = userRepository.findByLogin(login);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public UserEntity updateProfile(String currentLogin, String newLogin, String lastName, String firstName,
                                    String deliveryAddress, String email, String phone, String secondaryPhone) {
        UserEntity user = userRepository.findByLogin(currentLogin)
                .orElseThrow(() -> new IllegalArgumentException("error.user.notFound"));

        if (!currentLogin.equals(newLogin) && userRepository.existsByLogin(newLogin)) {
            throw new IllegalArgumentException("error.login.used");
        }

        user.setLogin(newLogin);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setDeliveryAddress(deliveryAddress);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSecondaryPhone(secondaryPhone);

        return userRepository.save(user);
    }

}