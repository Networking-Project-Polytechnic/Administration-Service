package com.example.administration.admin_auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminProfileDTO {
    private UUID id;
    private String name;
    private String email;
    private String role;
}
