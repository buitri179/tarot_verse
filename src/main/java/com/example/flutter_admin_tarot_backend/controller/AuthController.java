package com.example.flutter_admin_tarot_backend.controller;


import com.example.flutter_admin_tarot_backend.dto.LoginRequest;
import com.example.flutter_admin_tarot_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        if (token != null) {
            // Trả về JSON thay vì plain string
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Username or password incorrect"));
        }
    }
}
