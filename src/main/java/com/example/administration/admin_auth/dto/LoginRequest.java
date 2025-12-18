package com.example.administration.admin_auth.dto;

import lombok.Data; // Or use simple getters/setters

@Data
public class LoginRequest {
    private String name;    // Matches your UserEntity's email/username
    private String password;
}