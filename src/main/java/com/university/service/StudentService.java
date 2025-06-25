package com.university.service;

import com.university.model.Student;
import com.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injected here

    public Student registerNewStudent(Student student, String rawPassword) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("An account with this email already exists.");
        }

        // Password encoding happens in the service
        student.setPassword(passwordEncoder.encode(rawPassword));
        student.setStudentId(generateStudentId());

        return studentRepository.save(student);
    }

    /**
     * Updates an existing student's profile details.
     * Note: Password is not updated via this method.
     * Password changes should be a separate, secure process.
     */
    public Student updateStudentProfile(Student updatedStudent) {
        Student existingStudent = studentRepository.findById(updatedStudent.getId())
                .orElseThrow(() -> new IllegalStateException("Student not found for update."));

        // Check if the email is being changed and if the new email is already taken
        if (!existingStudent.getEmail().equalsIgnoreCase(updatedStudent.getEmail())) {
            if (studentRepository.existsByEmail(updatedStudent.getEmail())) {
                throw new IllegalStateException("This email address is already in use by another account.");
            }
            existingStudent.setEmail(updatedStudent.getEmail());
        }

        // Update non-sensitive profile information
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setDob(updatedStudent.getDob());

        return studentRepository.save(existingStudent);
    }

    private String generateStudentId() {
        String yearPrefix = String.valueOf(Year.now().getValue());
        Optional<Student> lastStudent = studentRepository.findTopByStudentIdStartingWithOrderByStudentIdDesc(yearPrefix);

        int nextId = lastStudent.map(s -> Integer.parseInt(s.getStudentId().substring(s.getStudentId().indexOf('-') + 1)) + 1).orElse(1);

        return String.format("%s-%04d", yearPrefix, nextId);
    }
}
