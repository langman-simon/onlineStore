package com.hyperion.controller;

import com.hyperion.session.Panel;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final Panel panel;

    public GlobalModelAttributes(Panel panel) {
        this.panel = panel;
    }

    @ModelAttribute("panel")
    public Panel panel() {
        return panel;
    }
}