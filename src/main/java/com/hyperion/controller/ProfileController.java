package com.hyperion.controller;

import com.hyperion.model.User;
import com.hyperion.service.GlobalBannerService;
import com.hyperion.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;
    private final GlobalBannerService globalBannerService;
    private final SecurityContextRepository securityContextRepository;

    public ProfileController(
            UserService userService,
            GlobalBannerService globalBannerService,
            SecurityContextRepository securityContextRepository
    ) {
        this.userService = userService;
        this.globalBannerService = globalBannerService;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/account")
    public String showAccount(
            Authentication authentication,
            Model model,
            HttpSession session
    ) {
        String currentLogin = authentication.getName();

        Optional<User> userOpt =
                userService.findByLogin(currentLogin);

        if (userOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "Le compte utilisateur est introuvable."
            );

            return "redirect:/";
        }

        model.addAttribute("user", userOpt.get());
        model.addAttribute("title", "Mon compte");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/account/account.jsp"
        );

        return "template/template";
    }

    @PostMapping("/account")
    public String updateAccount(
            Authentication authentication,
            @RequestParam String login,
            @RequestParam String lastName,
            @RequestParam String firstName,
            @RequestParam String deliveryAddress,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam(required = false) String secondaryPhone,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        String currentLogin = authentication.getName();
        String normalizedLogin = login.trim();

        try {
            userService.updateProfile(
                    currentLogin,
                    normalizedLogin,
                    lastName,
                    firstName,
                    deliveryAddress,
                    email,
                    phone,
                    secondaryPhone
            );

            Authentication newAuthentication =
                    new UsernamePasswordAuthenticationToken(
                            normalizedLogin,
                            authentication.getCredentials(),
                            authentication.getAuthorities()
                    );

            SecurityContext securityContext =
                    SecurityContextHolder.getContext();

            securityContext.setAuthentication(
                    newAuthentication
            );

            securityContextRepository.saveContext(
                    securityContext,
                    request,
                    response
            );

            globalBannerService.success(
                    session,
                    "Profil mis à jour avec succès."
            );

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );
        }

        return "redirect:/account";
    }
}
