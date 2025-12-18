package com.example.administration.admin_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role = "ADMIN";
}
