package com.university.controller;

import com.university.model.Student;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private StudentService studentService;

    /**
     * Displays the form to edit the student's profile.
     * The @AuthenticationPrincipal annotation injects the currently logged-in Student object.
     */
    @GetMapping("/profile/edit")
    public String showEditProfileForm(@AuthenticationPrincipal Student student, Model model) {
        // Add the current student's details to the model to pre-populate the form
        model.addAttribute("student", student);
        return "edit-profile";
    }

    /**
     * Handles the submission of the profile edit form.
     * Updates the student's details in the database.
     */
    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            Student updatedStudent = studentService.updateStudentProfile(student);

            // Refresh the security context with the updated student details.
            // This is crucial if the email (username) was changed.
            Authentication authentication = new UsernamePasswordAuthenticationToken(updatedStudent, updatedStudent.getPassword(), updatedStudent.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
            return "redirect:/dashboard"; // Redirect back to the dashboard
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating profile: " + e.getMessage());
            return "redirect:/profile/edit"; // Redirect back to the edit form with an error
        }
    }
}
