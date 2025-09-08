package com.example.flutter_admin_tarot_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // -> src/main/resources/templates/login.html
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; // -> src/main/resources/templates/dashboard.html
    }
}
