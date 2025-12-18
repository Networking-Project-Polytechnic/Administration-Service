package com.example.administration.admin_auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.administration.admin_auth.dto.AdminRequestDTO;
import com.example.administration.admin_auth.dto.JwtAuthenticationResponse;
import com.example.administration.admin_auth.dto.LoginRequest;
import com.example.administration.admin_auth.model.Admins;
import com.example.administration.admin_auth.security.JwtTokenProvider;
import com.example.administration.admin_auth.service.AdminsService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AdminsService adminsService;


    @PostMapping("admins/login")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginRequest loginRequest ){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getName(),
                loginRequest.getPassword()
            )
        );

        // If authentication is successful, set the security context (optional if using pure JWT)
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate the JWT token for the client to use in future requests
        String jwt = tokenProvider.generateToken(authentication);
        
        // Extract the role to send back to the frontend
        Admins userDetails = (Admins) authentication.getPrincipal();
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        // Return the token and role in the response body
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userRole));
    }

    @PostMapping("/admins/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRequestDTO adminDTO){
        
        try {
            adminsService.createAdmin(adminDTO);
            return new ResponseEntity<>("Administrator account created successfully", HttpStatus.CREATED);
        
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

