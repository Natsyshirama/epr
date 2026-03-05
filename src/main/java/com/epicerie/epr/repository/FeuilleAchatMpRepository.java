package com.epicerie.epr.repository;


import com.epicerie.epr.model.FeuilleAchatMp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeuilleAchatMpRepository extends JpaRepository<FeuilleAchatMp, Long> {

    Optional<FeuilleAchatMp> findTopByOrderByIdDesc();
}
