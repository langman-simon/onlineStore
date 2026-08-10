package com.hyperion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {

    @GetMapping("/company")
    public String company(Model model) {
        model.addAttribute("title", "Notre société");
        model.addAttribute("body", "/WEB-INF/jsp/company/company.jsp");

        return "template/template";
    }
}
