package com.example.administration.admin_auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.administration.admin_auth.model.Admins;

@Repository
public interface AdminsRepository extends JpaRepository<Admins, UUID> {
    Optional<Admins> findByName(String name);
    Optional<Admins> findByEmail(String email);
}
