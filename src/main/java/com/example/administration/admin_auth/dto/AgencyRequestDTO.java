package com.example.administration.admin_auth.dto;

import lombok.Data;


@Data
public class AgencyRequestDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
    private String address;
    private String licenseNumber;
    private String profileUrl;
    private String role= "ROLE_AGENCY"; // Default role for Agency
    private String status;
    private String bio;
}
