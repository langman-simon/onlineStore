package com.hyperion.service;

import com.hyperion.model.User;
import com.hyperion.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void shouldLoadRegularUserWithUserRole() {
        User user = new User();
        user.setLogin("neo");
        user.setPassword("hashed-password");
        user.setAdmin(false);

        when(userRepository.findByLogin("neo"))
                .thenReturn(Optional.of(user));

        UserDetails details = userDetailsService
                .loadUserByUsername("neo");

        assertThat(details.getUsername()).isEqualTo("neo");
        assertThat(details.getPassword())
                .isEqualTo("hashed-password");
        assertThat(details.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_USER");
    }

    @Test
    void shouldLoadAdminWithAdminAndUserRoles() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("hashed-password");
        user.setAdmin(true);

        when(userRepository.findByLogin("admin"))
                .thenReturn(Optional.of(user));

        UserDetails details = userDetailsService
                .loadUserByUsername("admin");

        assertThat(details.getAuthorities())
                .extracting("authority")
                .containsExactlyInAnyOrder(
                        "ROLE_ADMIN",
                        "ROLE_USER"
                );
    }

    @Test
    void shouldRejectUnknownUser() {
        when(userRepository.findByLogin("unknown"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                userDetailsService.loadUserByUsername("unknown")
        )
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("error.user.notFound");
    }
}
