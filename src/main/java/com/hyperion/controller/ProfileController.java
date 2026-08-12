package com.hyperion.controller;

import com.hyperion.dto.ProfileForm;
import com.hyperion.model.User;
import com.hyperion.service.GlobalBannerService;
import com.hyperion.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
        User user = userService
                .findByLogin(authentication.getName())
                .orElse(null);

        if (user == null) {
            globalBannerService.error(
                    session,
                    "error.account.notFound"
            );

            return "redirect:/";
        }

        model.addAttribute(
                "profileForm",
                ProfileForm.from(user)
        );
        prepareAccountPage(model);
        return "template/template";
    }

    @PostMapping("/account")
    public String updateAccount(
            Authentication authentication,
            @Valid @ModelAttribute("profileForm") ProfileForm form,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            globalBannerService.error(
                    session,
                    "error.form.invalid"
            );
            prepareAccountPage(model);
            return "template/template";
        }

        String currentLogin = authentication.getName();
        String normalizedLogin = form.getLogin().trim();

        try {
            userService.updateProfile(
                    currentLogin,
                    normalizedLogin,
                    form.getLastName().trim(),
                    form.getFirstName().trim(),
                    form.getDeliveryAddress().trim(),
                    form.getEmail().trim(),
                    form.getPhone().trim(),
                    normalizeOptional(form.getSecondaryPhone())
            );

            Authentication newAuthentication =
                    new UsernamePasswordAuthenticationToken(
                            normalizedLogin,
                            authentication.getCredentials(),
                            authentication.getAuthorities()
                    );

            SecurityContext securityContext =
                    SecurityContextHolder.getContext();
            securityContext.setAuthentication(newAuthentication);

            securityContextRepository.saveContext(
                    securityContext,
                    request,
                    response
            );

            globalBannerService.success(
                    session,
                    "message.account.updated"
            );

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );
        }

        return "redirect:/account";
    }

    private String normalizeOptional(String value) {
        return value == null || value.isBlank()
                ? null
                : value.trim();
    }

    private void prepareAccountPage(Model model) {
        model.addAttribute("titleKey", "page.account");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/account/account.jsp"
        );
    }
}
