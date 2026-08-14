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

        HttpSession session = request.getSession(true);
        String username = authentication != null
                ? authentication.getName()
                : null;

        if (username == null || username.isBlank()) {
            globalBannerService.success(
                    session,
                    "message.logout.success"
            );
        } else {
            globalBannerService.success(
                    session,
                    "message.logout.successUser",
                    username
            );
        }

        super.onLogoutSuccess(
                request,
                response,
                authentication
        );
    }
}
