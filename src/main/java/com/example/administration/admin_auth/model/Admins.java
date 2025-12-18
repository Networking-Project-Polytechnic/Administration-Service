package com.example.administration.admin_auth.model;


import java.util.Collection;
import java.util.List;
import java.util.UUID;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


// Import Spring Security interfaces
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
public class Admins implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String role = "ADMIN"; // Default role for Admins
   


    // =============================================
    // CONCRETE ADDITIONS FOR USERDETAILS INTERFACE
    // =============================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's role as a collection of authorities
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        // Specify which field serves as the username (for login)
        return this.name; 
    }
    
    // The following methods assume accounts are always non-expired, non-locked, 
    // and enabled by default. Adjust this logic if you implement features like 
    // account locking or email verification later.

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        // You could check the 'Status' enum here if you wanted to disable accounts
        return true; 
    }

}
