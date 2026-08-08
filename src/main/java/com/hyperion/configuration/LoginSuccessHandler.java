package com.hyperion.configuration;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    private final GlobalBannerService globalBannerService;
    private final RequestCache requestCache = new HttpSessionRequestCache();

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
                "Connexion réussie. Bienvenue "
                        + authentication.getName()
                        + " !"
        );

        String redirect = request.getParameter("redirect");

        if (isSafeLocalRedirect(redirect)) {
            requestCache.removeRequest(
                    request,
                    response
            );

            clearAuthenticationAttributes(request);

            response.sendRedirect(
                    request.getContextPath() + redirect
            );

            return;
        }

        super.onAuthenticationSuccess(
                request,
                response,
                authentication
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
