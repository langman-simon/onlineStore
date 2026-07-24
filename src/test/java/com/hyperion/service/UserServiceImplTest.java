package com.hyperion.service;

import com.hyperion.model.User;
import com.hyperion.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("jdupont");
        testUser.setPassword("hashedPassword123");
    }

    @Test
    void register_shouldSucceed_whenLoginDoesNotExist() {
        when(userRepository.existsByLogin("jdupont")).thenReturn(false);
        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.register(testUser, "plainPassword");

        assertNotNull(result);
        assertEquals("hashedPassword123", result.getPassword());
        verify(userRepository).save(testUser);
    }

    @Test
    void register_shouldThrowException_whenLoginAlreadyExists() {
        when(userRepository.existsByLogin("jdupont")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register(testUser, "plainPassword")
        );

        assertEquals("Login already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void authenticate_shouldReturnUser_whenCredentialsAreCorrect() {
        when(userRepository.findByLogin("jdupont")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("plainPassword", "hashedPassword123")).thenReturn(true);

        Optional<User> result = userService.authenticate("jdupont", "plainPassword");

        assertTrue(result.isPresent());
        assertEquals("jdupont", result.get().getLogin());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenPasswordIsWrong() {
        when(userRepository.findByLogin("jdupont")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword123")).thenReturn(false);

        Optional<User> result = userService.authenticate("jdupont", "wrongPassword");

        assertTrue(result.isEmpty());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenUserDoesNotExist() {
        when(userRepository.findByLogin("unknown")).thenReturn(Optional.empty());

        Optional<User> result = userService.authenticate("unknown", "anyPassword");

        assertTrue(result.isEmpty());
    }
}