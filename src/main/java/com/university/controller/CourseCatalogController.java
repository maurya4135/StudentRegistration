package com.university.controller;

import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.repository.CourseRepository;
import com.university.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CourseCatalogController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public CourseCatalogController(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/course-catalog")
    public String showCourseCatalog(Model model, @AuthenticationPrincipal Student student) {
        // Fetch all courses
        List<Course> allCourses = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "code"));
        model.addAttribute("allCourses", allCourses);

        // Fetch courses the student is already enrolled in to disable the enroll button
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
        Set<Long> enrolledCourseIds = enrollments.stream().map(enrollment -> enrollment.getCourse().getId()).collect(Collectors.toSet());
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);

        return "course-catalog";
    }
}