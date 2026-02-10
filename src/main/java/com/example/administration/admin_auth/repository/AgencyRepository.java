package com.example.administration.admin_auth.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.administration.admin_auth.agency_details.Status;
import com.example.administration.admin_auth.model.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, UUID>{
    Optional<Agency> findByUserName(String userName);
    Optional<Agency> findByEmail(String email);

    List<Agency> findByStatus(Status status);
}
