package com.university.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Course {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    private String description;
    private String instructor;
    private String daysTimes;
    private String location;
    private int capacity;
    private int enrolledCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type; // online, on-campus, hybrid
    private int credit;
    private double costPerCredit;
    private String semester; // e.g., Fall 2024, Spring 2025
    private String prerequisites;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDaysTimes() {
        return daysTimes;
    }

    public void setDaysTimes(String daysTimes) {
        this.daysTimes = daysTimes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getCostPerCredit() {
        return costPerCredit;
    }

    public void setCostPerCredit(double costPerCredit) {
        this.costPerCredit = costPerCredit;
    }
}
