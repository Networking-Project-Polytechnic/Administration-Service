package com.example.administration.admin_auth.service;



import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.administration.admin_auth.repository.AdminsRepository;
import lombok.RequiredArgsConstructor;

@Primary
@Service("customUserDetailsService") // Give it a specific name
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminsRepository adminsRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // Use your existing logic here
        return adminsRepository.findByName(name)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + name));
    }
}
