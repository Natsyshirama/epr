package com.epicerie.epr.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.epicerie.epr.model.AchatMp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    // Recherche par feuille et returner tous les achats de cette feuille
    List<AchatMp> findByFeuilleAchatMpId(Long feuilleId);

    // Vérifie si une feuille contient des achats
    boolean existsByFeuilleAchatMpId(Long feuilleId);

    // Récupère l'ID du vendeur associé à une feuille d'achat
    @Query("SELECT a.vendeur.id FROM AchatMp a WHERE a.feuilleAchatMp.id = :feuilleId")
    Optional<Long> findVendeurIdByFeuille(@Param("feuilleId") Long feuilleId);
}