package com.example.flutter_admin_tarot_backend.config;

import com.example.flutter_admin_tarot_backend.entity.Admin;
import com.example.flutter_admin_tarot_backend.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(AdminRepository adminRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (adminRepo.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin123"); // username mặc định
                admin.setPassword(passwordEncoder.encode("admin123")); // password: admin123
                adminRepo.save(admin);
                System.out.println("✅ Default admin created: username=admin123, password=admin123");
            } else {
                System.out.println("ℹ️ Admin already exists, skip initialization");
            }
        };
    }
}
