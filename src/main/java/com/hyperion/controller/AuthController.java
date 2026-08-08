package com.hyperion.controller;

import com.hyperion.dto.RegistrationForm;
import com.hyperion.model.User;
import com.hyperion.service.GlobalBannerService;
import com.hyperion.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final GlobalBannerService globalBannerService;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            GlobalBannerService globalBannerService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.globalBannerService = globalBannerService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Connexion");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/auth/login.jsp"
        );

        return "template/template";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute(
                "registrationForm",
                new RegistrationForm()
        );

        prepareRegisterPage(model);

        return "template/template";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("registrationForm")
            RegistrationForm form,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            globalBannerService.error(
                    session,
                    "Veuillez corriger les informations du formulaire."
            );

            prepareRegisterPage(model);

            return "template/template";
        }

        if (!form.getPassword()
                .equals(form.getPasswordConfirmation())) {

            globalBannerService.error(
                    session,
                    "Les mots de passe ne correspondent pas."
            );

            prepareRegisterPage(model);

            return "template/template";
        }

        if (userService.loginExists(
                form.getUsername().trim()
        )) {
            globalBannerService.error(
                    session,
                    "Ce pseudo est déjà utilisé."
            );

            prepareRegisterPage(model);

            return "template/template";
        }

        User user = new User();

        user.setLastName(
                form.getLastName().trim()
        );

        user.setFirstName(
                form.getFirstName().trim()
        );

        user.setDeliveryAddress(
                form.getDeliveryAddress().trim()
        );

        user.setEmail(
                form.getEmail().trim()
        );

        user.setPhone(
                form.getPhone().trim()
        );

        user.setLogin(
                form.getUsername().trim()
        );

        if (form.getSecondaryPhone() == null
                || form.getSecondaryPhone().isBlank()) {

            user.setSecondaryPhone(null);

        } else {
            user.setSecondaryPhone(
                    form.getSecondaryPhone().trim()
            );
        }

        userService.register(
                user,
                form.getPassword()
        );

        Authentication authentication =
                authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken
                                .unauthenticated(
                                        user.getLogin(),
                                        form.getPassword()
                                )
                );

        SecurityContext securityContext =
                SecurityContextHolder.createEmptyContext();

        securityContext.setAuthentication(
                authentication
        );

        SecurityContextHolder.setContext(
                securityContext
        );

        securityContextRepository.saveContext(
                securityContext,
                request,
                response
        );

        globalBannerService.success(
                session,
                "Compte créé, bienvenue "
                        + user.getLogin()
                        + "."
        );

        return "redirect:/";
    }

    private void prepareRegisterPage(Model model) {
        model.addAttribute(
                "title",
                "Inscription"
        );

        model.addAttribute(
                "body",
                "/WEB-INF/jsp/auth/register.jsp"
        );
    }
}