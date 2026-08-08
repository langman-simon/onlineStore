package com.hyperion.configuration;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final GlobalBannerService globalBannerService;

    public LoginFailureHandler(
            GlobalBannerService globalBannerService
    ) {
        this.globalBannerService = globalBannerService;
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

        String targetUrl = "/login";
        String redirect = request.getParameter("redirect");

        if (isSafeLocalRedirect(redirect)) {
            targetUrl += "?redirect="
                    + URLEncoder.encode(
                            redirect,
                            StandardCharsets.UTF_8
                    );
        }

        response.sendRedirect(
                request.getContextPath() + targetUrl
        );
    }

    private boolean isSafeLocalRedirect(String redirect) {
        return redirect != null
                && redirect.startsWith("/")
                && !redirect.startsWith("//")
                && !redirect.contains("\\")
                && !redirect.contains("\r")
                && !redirect.contains("\n");
    }
}
