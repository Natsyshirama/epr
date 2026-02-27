package com.epicerie.epr.service.vendeur;

import com.epicerie.epr.model.Vendeur;
import java.time.*;
import java.util.List;
public interface VendeurService {

    Vendeur createVendeur(String nom, String phone, String info, LocalDate dateCreated);

    Vendeur updateVendeur(Long id, String nom, String phone, String info, LocalDate dateCreated);

    List<Vendeur> getAll();

    Vendeur getById(Long id);
    
    void deleteVendeur(Long id);

}
