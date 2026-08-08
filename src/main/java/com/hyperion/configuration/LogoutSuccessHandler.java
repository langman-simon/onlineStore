package com.hyperion.configuration;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogoutSuccessHandler
        extends SimpleUrlLogoutSuccessHandler {

    private final GlobalBannerService globalBannerService;

    public LogoutSuccessHandler(
            GlobalBannerService globalBannerService
    ) {
        this.globalBannerService = globalBannerService;

        setDefaultTargetUrl("/");
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        String username = authentication != null
                ? authentication.getName()
                : null;

        HttpSession session = request.getSession(true);

        String message = username == null || username.isBlank()
                ? "Déconnexion réussie. Au revoir !"
                : "Déconnexion réussie. Au revoir " + username + " !";

        globalBannerService.success(
                session,
                message
        );

        super.onLogoutSuccess(
                request,
                response,
                authentication
        );
    }
}
