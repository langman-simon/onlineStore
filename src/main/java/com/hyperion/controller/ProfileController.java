package com.hyperion.controller;

import com.hyperion.model.User;
import com.hyperion.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.hyperion.service.GlobalBannerService;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;
    private final GlobalBannerService globalBannerService;

    public ProfileController(
            UserService userService,
            GlobalBannerService globalBannerService
    ) {
        this.userService = userService;
        this.globalBannerService = globalBannerService;
    }

    @GetMapping("/account")
    public String showAccount(Authentication authentication, Model model) {
        String currentLogin = authentication.getName();
        Optional<User> userOpt = userService.findByLogin(currentLogin);

        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("user", userOpt.get());
        model.addAttribute("title", "Mon compte");
        model.addAttribute("body", "/WEB-INF/jsp/account/account.jsp");
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
            HttpSession session
    ) {
        String currentLogin = authentication.getName();

        try {
            userService.updateProfile(
                    currentLogin,
                    login,
                    lastName,
                    firstName,
                    deliveryAddress,
                    email,
                    phone,
                    secondaryPhone
            );

            // Refresh Spring Security authentication with the new login
            Authentication newAuth =
                    new UsernamePasswordAuthenticationToken(
                            login,
                            authentication.getCredentials(),
                            authentication.getAuthorities()
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(newAuth);

            globalBannerService.success(
                    session,
                    "Profile updated successfully"
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