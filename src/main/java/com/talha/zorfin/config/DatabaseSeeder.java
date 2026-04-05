package com.talha.zorfin.config;

import org.springframework.boot.CommandLineRunner;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.talha.zorfin.entity.User;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;
import com.talha.zorfin.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default-email}")
    private String email;

    @Value("${admin.default-password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepo.existsByRole(UserRole.ADMIN)) {
            log.info("Admin not found. seeding a default one....");

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(UserRole.ADMIN);
            admin.setStatus(UserStatus.ACTIVE);
            admin.setCreatedAt(Instant.now());

            userRepo.save(admin);
            log.info("Default admin created with email: {}", email);
        } else {
            log.info("Admin user already exists. Skipping admin seeding.");
        }

    }
    
}
