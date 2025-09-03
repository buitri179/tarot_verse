package com.example.flutter_admin_tarot_backend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456a";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("BCrypt hash: " + encodedPassword);
    }
}