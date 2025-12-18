package com.example.administration.admin_auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.administration.admin_auth.dto.AdminRequestDTO;
import com.example.administration.admin_auth.dto.AdminRequestDTOMapper;
import com.example.administration.admin_auth.model.Admins;
import com.example.administration.admin_auth.repository.AdminsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminsService implements UserDetailsService{
    private final AdminsRepository adminsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRequestDTOMapper adminRequestDTOMapper;

    @Override
    public UserDetails loadUserByUsername(String name) {

        Optional<Admins> user =  adminsRepository.findByName(name);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                .username(userObj.getUsername())
                .password(userObj.getPassword())
                .roles(userObj.getRole())
                .build();
        }else{
            throw new UsernameNotFoundException(name);
        }
    }

    public Admins createAdmin(AdminRequestDTO adminDTO){
        if (adminsRepository.findByEmail(adminDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Admin with this email already exists");
        }

        if(adminsRepository.findByName(adminDTO.getName()).isPresent()){
            throw new IllegalArgumentException("Admin with this name already exists");
        }
        Admins admin = adminRequestDTOMapper.toEntity(adminDTO);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        
        return adminsRepository.save(admin);
    }
}
