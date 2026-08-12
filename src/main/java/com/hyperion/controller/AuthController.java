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
import org.springframework.security.core.AuthenticationException;
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
        model.addAttribute("titleKey", "page.login");
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
            result.getAllErrors().stream()
                    .findFirst()
                    .ifPresentOrElse(
                            error -> globalBannerService.error(
                                    session,
                                    error
                            ),
                            () -> globalBannerService.error(
                                    session,
                                    "error.form.invalid"
                            )
                    );

            prepareRegisterPage(model);

            return "template/template";
        }

        if (!form.getPassword()
                .equals(form.getPasswordConfirmation())) {
            result.rejectValue(
                    "passwordConfirmation",
                    "error.password.mismatch"
            );

            globalBannerService.error(
                    session,
                    "error.password.mismatch"
            );

            prepareRegisterPage(model);

            return "template/template";
        }

        String normalizedLogin =
                form.getUsername().trim();

        User user = new User();

        user.setLastName(form.getLastName().trim());
        user.setFirstName(form.getFirstName().trim());
        user.setDeliveryAddress(
                form.getDeliveryAddress().trim()
        );
        user.setEmail(form.getEmail().trim());
        user.setPhone(form.getPhone().trim());
        user.setLogin(normalizedLogin);

        if (form.getSecondaryPhone() == null
                || form.getSecondaryPhone().isBlank()) {
            user.setSecondaryPhone(null);
        } else {
            user.setSecondaryPhone(
                    form.getSecondaryPhone().trim()
            );
        }

        try {
            userService.register(
                    user,
                    form.getPassword()
            );

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );

            prepareRegisterPage(model);

            return "template/template";
        }

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            UsernamePasswordAuthenticationToken
                                    .unauthenticated(
                                            user.getLogin(),
                                            form.getPassword()
                                    )
                    );

            SecurityContext securityContext =
                    SecurityContextHolder
                            .createEmptyContext();

            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            securityContextRepository.saveContext(
                    securityContext,
                    request,
                    response
            );

            globalBannerService.success(
                    session,
                    "message.register.success",
                    user.getLogin()
            );

            return "redirect:/";

        } catch (AuthenticationException exception) {
            globalBannerService.warning(
                    session,
                    "message.register.loginRequired"
            );

            return "redirect:/login";
        }
    }

    private void prepareRegisterPage(Model model) {
        model.addAttribute("titleKey", "page.register");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/auth/register.jsp"
        );
    }
}
