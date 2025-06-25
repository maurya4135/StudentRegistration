package com.university.controller;

import com.university.model.Student;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal Student student, Model model) {
        // Spring Security, via the CustomUserDetailsService, provides the
        // authenticated Student object directly into the controller method.
        model.addAttribute("student", student);
        return "dashboard";
    }
}