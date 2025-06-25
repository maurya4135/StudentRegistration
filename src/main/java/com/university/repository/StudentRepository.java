package com.university.repository;

import com.university.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Finds the student with the highest studentId for a given year prefix (e.g., "2024")
    Optional<Student> findTopByStudentIdStartingWithOrderByStudentIdDesc(String yearPrefix);

    // Finds a student by their email address, which is typically used as the username.
    Optional<Student> findByEmail(String email);

    // Checks if a student with the given email already exists. More efficient than findByEmail for validation.
    boolean existsByEmail(String email);
}
