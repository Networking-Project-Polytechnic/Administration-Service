package com.example.administration.admin_auth.dto;

import org.springframework.stereotype.Component;

import com.example.administration.admin_auth.model.Admins;

@Component
public class AdminRequestDTOMapper {

    public Admins toEntity(AdminRequestDTO dto){
        Admins admin = new Admins();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());
        
        return admin;
    }

    public AdminRequestDTO toDTO(Admins admin){
        AdminRequestDTO admin_dto = new AdminRequestDTO();
        admin_dto.setName(admin.getName());
        admin_dto.setEmail(admin.getEmail());
        admin_dto.setPassword(admin.getPassword());


        return admin_dto;
    }
}
