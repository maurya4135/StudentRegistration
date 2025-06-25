package com.university.controller;

import com.university.model.Student;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Correct import for Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        try {
            // Delegate password encoding and student ID generation to the service
            Student registeredStudent = studentService.registerNewStudent(student, password);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Your Student ID is: " + registeredStudent.getStudentId() + ". Please log in.");
            return "redirect:/login"; // Redirect to login page on success
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register"; // Redirect back to registration page with error
        }
    }
}
