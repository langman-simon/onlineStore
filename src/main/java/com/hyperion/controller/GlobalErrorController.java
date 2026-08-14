package com.hyperion.controller;

import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

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
