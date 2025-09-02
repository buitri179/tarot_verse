package com.example.flutter_admin_tarot_backend.service;


import com.example.flutter_admin_tarot_backend.entity.Admin;
import com.example.flutter_admin_tarot_backend.repository.AdminRepository;
import com.example.flutter_admin_tarot_backend.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AdminRepository adminRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(AdminRepository adminRepo, JwtUtil jwtUtil) {
        this.adminRepo = adminRepo;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        return adminRepo.findByUsername(username)
                .filter(admin -> encoder.matches(password, admin.getPassword()))
                .map(admin -> jwtUtil.generateToken(admin.getUsername()))
                .orElse(null); // trả null nếu login thất bại
    }
}
