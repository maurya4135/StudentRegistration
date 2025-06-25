package com.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Allow access to static resources and public pages
                .requestMatchers("/css/**", "/js/**", "/register", "/login", "/").permitAll()
                // All other requests must be authenticated
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Specify the custom login page
                .loginPage("/login")
                // Specify the parameter name for the username (which is email in our case)
                .usernameParameter("email")
                // The URL to submit the username and password to
                .loginProcessingUrl("/login")
                // The page to redirect to on successful login
                .defaultSuccessUrl("/dashboard", true)
                // The page to redirect to on failed login
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                // The URL to redirect to after the user logs out
                .logoutSuccessUrl("/login?logout")
                // Invalidate the session on logout
                .invalidateHttpSession(true)
                // Clear authentication on logout
                .clearAuthentication(true)
                .permitAll()
            );
        return http.build();
    }
}
