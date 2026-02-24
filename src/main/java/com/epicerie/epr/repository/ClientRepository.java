package com.epicerie.epr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.epicerie.epr.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}