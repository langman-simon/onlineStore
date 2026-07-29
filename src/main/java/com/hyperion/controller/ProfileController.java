package com.hyperion.controller;

import com.hyperion.model.User;
import com.hyperion.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String showAccount(Authentication authentication, Model model) {
        String login = authentication.getName();
        Optional<User> userOpt = userService.findByLogin(login);

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
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String deliveryAddress,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam(required = false) String secondaryPhone,
                                Model model) {
        String login = authentication.getName();

        userService.updateProfile(login, lastName, firstName, deliveryAddress, email, phone, secondaryPhone);

        model.addAttribute("success", "Profile updated successfully");
        model.addAttribute("user", userService.findByLogin(login).get());
        model.addAttribute("title", "Mon compte");
        model.addAttribute("body", "/WEB-INF/jsp/account/account.jsp");
        return "template/template";
    }
}