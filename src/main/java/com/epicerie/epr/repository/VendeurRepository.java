package com.epicerie.epr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.epicerie.epr.model.Vendeur;
public interface VendeurRepository extends JpaRepository<Vendeur, Long> {

}
