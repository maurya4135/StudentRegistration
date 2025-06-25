# Student Registration System

A modern and responsive web application for student registration and login, built with Java, Spring Boot, and Thymeleaf.

## Description

This project provides a clean and intuitive user interface for a student management system. It features a two-panel layout that allows new students to create an account and existing students to log in. The application is designed to be fully responsive, ensuring a seamless experience on both desktop and mobile devices.

## Features

-   **User Registration**: A secure form for new students to sign up by providing their first name, last name, email, date of birth, and a password.
-   **User Login**: An authentication page for existing students to access their accounts.
-   **Modern & Responsive UI**: The interface uses a professional two-panel design that adapts to different screen sizes.
-   **Dynamic Feedback**: Displays success or error messages to the user after form submissions using Thymeleaf.

## Technologies Used

-   **Backend**: Java, Spring Boot
-   **Frontend**: HTML5, CSS3
-   **Templating Engine**: Thymeleaf
-   **Build Tool**: Apache Maven

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

-   Java Development Kit (JDK) 11 or later
-   Apache Maven

### Installation

1.  Clone the repository to your local machine.
2.  Navigate to the project's root directory in your terminal.
3.  Run the application using the Maven wrapper:
    ```sh
    mvn clean install
    mvn spring-boot:run

    ```
4.  Open your web browser and navigate to `http://localhost:8080`.

## Application Pages

-   **Login Page**: `http://localhost:8080/login`
-   **Registration Page**: `http://localhost:8080/register`
