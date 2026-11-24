package com.hyperion.service;

import com.hyperion.dataAccess.dao.UserDataAccess;
import com.hyperion.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDataAccess userDataAccess;

    public CustomUserDetailsService(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        User user = userDataAccess.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "error.user.notFound"
                        )
                );

        String[] roles = user.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}