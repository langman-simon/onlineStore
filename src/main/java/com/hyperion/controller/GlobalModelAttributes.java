package com.hyperion.controller;

import com.hyperion.session.SessionCart;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final SessionCart sessionCart;

    public GlobalModelAttributes(SessionCart sessionCart) {
        this.sessionCart = sessionCart;
    }

    @ModelAttribute("sessionCart")
    public SessionCart sessionCart() {
        return sessionCart;
    }
}
