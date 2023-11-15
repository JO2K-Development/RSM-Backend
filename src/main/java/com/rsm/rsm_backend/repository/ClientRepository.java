package com.rsm.rsm_backend.repository;

import com.rsm.rsm_backend.entity.Client;
import com.rsm.rsm_backend.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Provider> findByEmail(String email);
}
