package com.hyperion.service;

import com.hyperion.model.User;
import com.hyperion.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User inscrire(User user, String motDePasseClair) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Ce login est déjà utilisé");
        }
        user.setMotDePasse(passwordEncoder.encode(motDePasseClair));
        return userRepository.save(user);
    }

    @Override
    public boolean loginExiste(String login) {
        return userRepository.existsByLogin(login);
    }
}