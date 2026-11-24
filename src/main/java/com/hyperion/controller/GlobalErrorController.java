package com.hyperion.controller;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorController.class);

    private final GlobalBannerService globalBannerService;

    public GlobalErrorController(
            GlobalBannerService globalBannerService
    ) {
        this.globalBannerService = globalBannerService;
    }

    @RequestMapping("/error")
    public String handleError(
            HttpServletRequest request,
            HttpSession session
    ) {
        Object statusAttribute =
                request.getAttribute(
                        RequestDispatcher.ERROR_STATUS_CODE
                );

        int status = statusAttribute != null
                ? Integer.parseInt(statusAttribute.toString())
                : 500;

        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        log.error("Erreur {} sur {}", status, uri, exception);

        String messageCode = switch (status) {
            case 400 -> "error.badRequest";
            case 403 -> "error.accessDenied";
            case 404 -> "error.notFound";
            case 405 -> "error.methodNotAllowed";
            case 500 -> "error.internal";
            default -> "error.generic";
        };

        globalBannerService.error(
                session,
                messageCode,
                status
        );

        return "redirect:/";
    }
}