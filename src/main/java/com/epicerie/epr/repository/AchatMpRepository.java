package com.epicerie.epr.repository;

import java.time.LocalDate;
import java.util.List;

import com.epicerie.epr.model.AchatMp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchatMpRepository extends JpaRepository<AchatMp, Long> {
    // Recherche par vendeur
    List<AchatMp> findByVendeurId(Long vendeurId);

    // Recherche par type
    List<AchatMp> findByTypeId(Long typeId);

    // Recherche par période
    List<AchatMp> findByDateAchatBetween(
            LocalDate start,
            LocalDate end
    );
}