package com.example.flutter_admin_tarot_backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}