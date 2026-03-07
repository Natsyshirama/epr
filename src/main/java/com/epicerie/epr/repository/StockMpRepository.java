package com.epicerie.epr.repository;

import com.epicerie.epr.model.StockMp;
import com.epicerie.epr.model.TypeMp;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface StockMpRepository extends JpaRepository<StockMp, Long> {
    //recuperer le stock d'une matière première par son type
    Optional<StockMp> findByTypeMp(TypeMp typeMp);

}
