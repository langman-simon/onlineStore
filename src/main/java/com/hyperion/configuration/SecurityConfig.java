package com.hyperion.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final GlobalAccessDeniedHandler globalAccessDeniedHandler;

    public SecurityConfig(
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            LogoutSuccessHandler logoutSuccessHandler,
            GlobalAccessDeniedHandler globalAccessDeniedHandler
    ) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.globalAccessDeniedHandler = globalAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/payment/paypal/ipn"
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/order/**",
                                "/account/**"
                        )
                        .authenticated()

                        .requestMatchers("/cart/**")
                        .permitAll()

                        .anyRequest()
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(globalAccessDeniedHandler)
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .permitAll()
                );

        return http.build();
    }
}
