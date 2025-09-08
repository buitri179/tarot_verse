package com.example.flutter_admin_tarot_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login.html"; // chuyển hướng đến login.html
    }
}

