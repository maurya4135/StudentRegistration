package com.university.service;

import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.repository.CourseRepository;
import com.university.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Enrollment enrollStudent(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        // 1. Check if student is already enrolled
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalStateException("You are already enrolled in this course.");
        }

        // 2. Check for course capacity
        if (course.getEnrolledCount() >= course.getCapacity()) {
            throw new IllegalStateException("Sorry, this course is full.");
        }

        // 3. Create new enrollment record
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        // 4. Update course's enrolled count and save
        course.setEnrolledCount(course.getEnrolledCount() + 1);
        courseRepository.save(course);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void dropStudent(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        // 1. Find the specific enrollment record
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new IllegalStateException("You are not enrolled in this course. Cannot drop."));

        // 2. Delete the enrollment record
        enrollmentRepository.delete(enrollment);

        // 3. Update the course's enrolled count
        // Ensure count doesn't go below zero, though it shouldn't with this logic
        if (course.getEnrolledCount() > 0) {
            course.setEnrolledCount(course.getEnrolledCount() - 1);
            courseRepository.save(course);
        }
    }
}