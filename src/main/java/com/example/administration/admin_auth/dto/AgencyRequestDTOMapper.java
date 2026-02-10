package com.example.administration.admin_auth.dto;

import com.example.administration.admin_auth.agency_details.Status;
import com.example.administration.admin_auth.model.Agency;

import org.springframework.stereotype.Service;

@Service
public class AgencyRequestDTOMapper {

    public Agency toEntity(AgencyRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Agency agency = new Agency();
        agency.setUserName(dto.getUserName());
        agency.setFirstName(dto.getFirstName());
        agency.setLastName(dto.getLastName());
        agency.setEmail(dto.getEmail());
        agency.setPhoneNumber(dto.getPhoneNumber());
        agency.setAddress(dto.getAddress());
        agency.setLicenseNumber(dto.getLicenseNumber());
        agency.setProfileUrl(dto.getProfileUrl());
        agency.setRole(dto.getRole());
        agency.setStatus(Status.valueOf(dto.getStatus()));
        agency.setBio(dto.getBio());

        return agency;
    }

    public AgencyRequestDTO toDTO(Agency agency) {
        if (agency == null) {
            return null;
        }
        
        AgencyRequestDTO dto = new AgencyRequestDTO();
        dto.setUserName(agency.getUsername());
        dto.setFirstName(agency.getFirstName());
        dto.setLastName(agency.getLastName());
        dto.setEmail(agency.getEmail());
        dto.setPhoneNumber(agency.getPhoneNumber());
        dto.setAddress(agency.getAddress());
        dto.setLicenseNumber(agency.getLicenseNumber());
        dto.setProfileUrl(agency.getProfileUrl());
        dto.setRole(agency.getRole());
        dto.setStatus(agency.getStatus().name());
        dto.setBio(agency.getBio());

        return dto;
    }
}
