package com.hyperion.configuration;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    private final GlobalBannerService globalBannerService;

    public LoginFailureHandler(
            GlobalBannerService globalBannerService
    ) {
        this.globalBannerService = globalBannerService;

        setDefaultFailureUrl("/login");
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        globalBannerService.error(
                request.getSession(),
                "Identifiant ou mot de passe incorrect."
        );

        super.onAuthenticationFailure(
                request,
                response,
                exception
        );
    }
}