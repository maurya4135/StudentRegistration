package com.university.controller;

import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ScheduleController {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public ScheduleController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/schedule")
    public String showSchedule(Model model, @AuthenticationPrincipal Student student) {
        // Fetch all enrollments for the current student
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        // Extract the list of courses from the enrollments, then sort them by course code
        List<Course> enrolledCourses = enrollments.stream()
                .map(Enrollment::getCourse)
                .sorted(Comparator.comparing(Course::getCode))
                .collect(Collectors.toList());

        model.addAttribute("enrolledCourses", enrolledCourses);
        return "schedule";
    }
}