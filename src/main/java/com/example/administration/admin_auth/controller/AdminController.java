package com.example.administration.admin_auth.controller;

import java.util.List;
import java.util.Optional;
import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.administration.admin_auth.agency_details.Status;
import com.example.administration.admin_auth.dto.AdminRequestDTO;
import com.example.administration.admin_auth.dto.JwtAuthenticationResponse;
import com.example.administration.admin_auth.dto.LoginRequest;
import com.example.administration.admin_auth.dto.AdminProfileDTO;
import com.example.administration.admin_auth.model.Admins;
import com.example.administration.admin_auth.model.Agency;
import com.example.administration.admin_auth.security.JwtTokenProvider;
import com.example.administration.admin_auth.service.AdminsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admins")
public class AdminController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AdminsService adminsService;


    @PostMapping("/login")
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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        // Return the token and role in the response body
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userRole));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRequestDTO adminDTO){
        
        try {
            adminsService.createAdmin(adminDTO);
            return new ResponseEntity<>("Administrator account created successfully", HttpStatus.CREATED);
        
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/agencies")
    public List<Agency> getAllAgencies() {
        return adminsService.getAllAgencies();
    }
    
    @GetMapping("/agencies-by-status")
    public List<Agency> getAllAgencies(@RequestParam Status status) {
        return adminsService.getAgenciesByStatus(status);
    }

    @PutMapping("/agencies/{userName}")
    public ResponseEntity<?> updateAgencyStatus(@PathVariable String userName, @RequestParam Status newStatus) {
        Optional<Agency> updatedAgency = adminsService.updateAgencyStatus(userName, newStatus);
        if (updatedAgency.isPresent()) {
            return ResponseEntity.ok(updatedAgency.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/profile")
    public ResponseEntity<AdminProfileDTO> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        AdminProfileDTO profile = adminsService.getAdminProfile(username);
        return ResponseEntity.ok(profile);
    }

}
