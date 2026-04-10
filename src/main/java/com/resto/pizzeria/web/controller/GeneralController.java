package com.resto.pizzeria.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GeneralController {
    @GetMapping
    public String homePage(Model model) {
        return "pages/homepage";
    }
}
