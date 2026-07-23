package com.hyperion.controller;

import com.hyperion.model.User;
import com.hyperion.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hyperion.dto.RegistrationForm;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String login,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        Optional<User> userOpt = userService.authenticate(login, password);

        if (userOpt.isEmpty()) {
            model.addAttribute("title", "Connexion");
            model.addAttribute("body", "/WEB-INF/jsp/auth/login.jsp");
            model.addAttribute("error", "Invalid login or password");
            return "template/template";
        }

        User user = userOpt.get();
        session.setAttribute("login", user.getLogin());
        session.setAttribute("isAdmin", user.isAdmin());

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Connexion");
        model.addAttribute("body", "/WEB-INF/jsp/auth/login.jsp");

        return "template/template";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
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
            model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");
            return "template/template";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("title", "Inscription");
            model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");
            model.addAttribute("error", "Passwords do not match");
            return "template/template";
        }

        if (userService.loginExists(form.getLogin())) {
            model.addAttribute("title", "Inscription");
            model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");
            model.addAttribute("error", "Login already exists");
            return "template/template";
        }

        User user = new User();
        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setDeliveryAddress(form.getDeliveryAddress());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setLogin(form.getLogin());
        user.setSecondaryPhone(form.getSecondaryPhone());

        userService.register(user, form.getPassword());

        return "redirect:/login";
    }
}