package com.example.user_control.config;

import com.example.user_control.entity.Role;
import com.example.user_control.entity.User;
import com.example.user_control.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository) {
        return args -> {

            if (!userRepository.existsByEmail(adminEmail)) {

                User admin = User.builder()
                        .username(adminUsername)
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);

                System.out.println("Admin user created ✅");
            }
        };
    }
}

