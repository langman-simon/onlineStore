package com.hyperion.service;

import com.hyperion.model.User;
import com.hyperion.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(
                userRepository,
                passwordEncoder
        );
    }

    @Test
    void shouldHashPasswordBeforeRegistration() {
        User user = new User();
        user.setLogin("neo");

        when(userRepository.existsByLogin("neo"))
                .thenReturn(false);
        when(passwordEncoder.encode("secret123"))
                .thenReturn("hashed-password");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.register(
                user,
                "secret123"
        );

        assertThat(saved.getPassword())
                .isEqualTo("hashed-password");
        verify(passwordEncoder).encode("secret123");
        verify(userRepository).save(user);
    }

    @Test
    void shouldRejectDuplicateLoginOnRegistration() {
        User user = new User();
        user.setLogin("neo");

        when(userRepository.existsByLogin("neo"))
                .thenReturn(true);

        assertThatThrownBy(() ->
                userService.register(user, "secret123")
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.login.used");
    }
}
