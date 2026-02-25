package com.epicerie.epr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.epicerie.epr.model.StatusPayment;
import java.util.Optional;

public interface StatusPayementRepository extends JpaRepository<StatusPayment, Long> {
        Optional<StatusPayment> findByNom(String nom);

}