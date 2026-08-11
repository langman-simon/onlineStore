package com.hyperion.configuration;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GlobalAccessDeniedHandler implements AccessDeniedHandler {

    private final GlobalBannerService globalBannerService;

    public GlobalAccessDeniedHandler(
            GlobalBannerService globalBannerService
    ) {
        this.globalBannerService = globalBannerService;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        globalBannerService.error(
                request.getSession(),
                "error.accessDenied"
        );

        response.sendRedirect(
                request.getContextPath() + "/"
        );
    }
}
