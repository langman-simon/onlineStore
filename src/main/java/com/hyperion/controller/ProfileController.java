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

import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
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
    public String updateAccount(Authentication authentication,
                                @RequestParam String login,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String deliveryAddress,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam(required = false) String secondaryPhone,
                                Model model) {
        String currentLogin = authentication.getName();

        try {
            userService.updateProfile(currentLogin, login, lastName, firstName, deliveryAddress, email, phone, secondaryPhone);

            // Rafraîchit la session Spring Security avec le nouveau login
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    login,
                    authentication.getCredentials(),
                    authentication.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);

        } catch (IllegalArgumentException e) {
            model.addAttribute("title", "Mon compte");
            model.addAttribute("body", "/WEB-INF/jsp/account/account.jsp");
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", userService.findByLogin(currentLogin).get());
            return "template/template";
        }

        model.addAttribute("success", "Profile updated successfully");
        model.addAttribute("user", userService.findByLogin(login).get());
        model.addAttribute("title", "Mon compte");
        model.addAttribute("body", "/WEB-INF/jsp/account/account.jsp");
        return "template/template";
    }
}