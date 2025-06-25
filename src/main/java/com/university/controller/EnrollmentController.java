package com.university.controller;

import com.university.model.Student;
import com.university.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll/{courseId}")
    public String enrollInCourse(@PathVariable Long courseId,
                                 @AuthenticationPrincipal Student student,
                                 RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.enrollStudent(student, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully enrolled in the course!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/course-catalog";
    }

    @PostMapping("/drop/{courseId}")
    public String dropCourse(@PathVariable Long courseId,
                             @AuthenticationPrincipal Student student,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        try {
            enrollmentService.dropStudent(student, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully dropped the course.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        // Redirect back to the page the user came from (e.g., /course-catalog or /schedule)
        // This provides a better user experience.
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        
        // Fallback to the course catalog if the referer header is not available
        return "redirect:/course-catalog";
    }
}