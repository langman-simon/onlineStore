package com.hyperion.controller;

import com.hyperion.session.SessionCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class GlobalModelAttributes {

    private static final String BANNER_MESSAGE = "globalBannerMessage";
    private static final String BANNER_TYPE = "globalBannerType";

    private final SessionCart sessionCart;

    public GlobalModelAttributes(SessionCart sessionCart) {
        this.sessionCart = sessionCart;
    }

    @ModelAttribute("sessionCart")
    public SessionCart sessionCart() {
        return sessionCart;
    }

    @ModelAttribute("frUrl")
    public String frUrl() {
        return currentUrlWithLang("fr");
    }

    @ModelAttribute("enUrl")
    public String enUrl() {
        return currentUrlWithLang("en");
    }

    @ModelAttribute
    public void globalBanner(HttpSession session, Model model) {
        Object message = session.getAttribute(BANNER_MESSAGE);

        if (message != null) {
            model.addAttribute(BANNER_MESSAGE, message);
            model.addAttribute(BANNER_TYPE, session.getAttribute(BANNER_TYPE));
            session.removeAttribute(BANNER_MESSAGE);
            session.removeAttribute(BANNER_TYPE);
        }
    }

    private String currentUrlWithLang(String lang) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam("lang", lang)
                .build(true)
                .toUriString();
    }
}