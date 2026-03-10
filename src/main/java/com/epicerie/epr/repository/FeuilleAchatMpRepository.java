package com.epicerie.epr.repository;


import com.epicerie.epr.model.FeuilleAchatMp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface FeuilleAchatMpRepository extends JpaRepository<FeuilleAchatMp, Long> {
    @Query("SELECT DISTINCT f FROM FeuilleAchatMp f JOIN f.achats a WHERE a.vendeur.id = :vendeurId")
    List<FeuilleAchatMp> findDistinctByAchatsVendeurId(@Param("vendeurId") Long vendeurId);
    //recupérer les feuilles d'achat validées d'un vendeur
    List<FeuilleAchatMp> findByVendeurIdAndValideTrueOrderByDateCreationAsc(Long vendeurId);

    Optional<FeuilleAchatMp> findTopByOrderByIdDesc();
}
