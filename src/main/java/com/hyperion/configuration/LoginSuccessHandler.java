package com.hyperion.configuration;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final GlobalBannerService globalBannerService;

    public LoginSuccessHandler(
            GlobalBannerService globalBannerService
    ) {
        this.globalBannerService = globalBannerService;

        setDefaultTargetUrl("/");
        setAlwaysUseDefaultTargetUrl(false);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        globalBannerService.success(
                request.getSession(),
                "Connexion réussie. Bienvenue " + authentication.getName() + " !"
        );

        super.onAuthenticationSuccess(
                request,
                response,
                authentication
        );
    }
}