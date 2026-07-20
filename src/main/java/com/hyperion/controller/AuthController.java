package com.hyperion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Connexion");
        model.addAttribute("body", "/WEB-INF/jsp/auth/login.jsp");

        return "template/template";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Inscription");
        model.addAttribute("body", "/WEB-INF/jsp/auth/register.jsp");

        return "template/template";
    }
}