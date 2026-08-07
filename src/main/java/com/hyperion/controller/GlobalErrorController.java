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

        String message = switch (status) {
            case 400 ->
                    "Requête invalide.";

            case 403 ->
                    "Accès refusé. Vous ne disposez pas des droits nécessaires.";

            case 404 ->
                    "La page demandée est introuvable.";

            case 405 ->
                    "Cette action n'est pas autorisée.";

            case 500 ->
                    "Une erreur interne est survenue.";

            default ->
                    "Une erreur est survenue. Code : " + status;
        };

        globalBannerService.error(
                session,
                message
        );

        return "redirect:/";
    }
}