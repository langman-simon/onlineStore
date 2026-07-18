package com.hyperion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class PanelController {

    @GetMapping
    public String showPanel() {
        return "panel";
    }

}