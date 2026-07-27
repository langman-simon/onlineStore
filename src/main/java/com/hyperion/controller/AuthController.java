package com.hyperion.controller;

import com.hyperion.dto.RegistrationForm;
import com.hyperion.model.User;
import com.hyperion.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("title", "Connexion");
        model.addAttribute("body", "/WEB-INF/jsp/auth/login.jsp");
        if (error != null) {
            model.addAttribute("error", "Identifiant ou mot de passe incorrect.");
        }
        return "template/template";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Inscription");
        model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");
        return "template/template";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute RegistrationForm form,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Inscription");
            model.addAttribute("body", "/WEB-INF/jsp/auth/home.jsp");
            return "template/template";
        }

        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            model.addAttribute("title", "Inscription");
            model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");
            model.addAttribute("error", "Passwords do not match");
            return "template/template";
        }

        if (userService.loginExists(form.getUsername())) {
            model.addAttribute("title", "Inscription");
            model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");
            model.addAttribute("error", "Username already exists");
            return "template/template";
        }

        User user = new User();
        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setDeliveryAddress(form.getDeliveryAddress());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setLogin(form.getUsername());
        user.setSecondaryPhone(form.getSecondaryPhone());

        userService.register(user, form.getPassword());

        return "redirect:/login";
    }
}