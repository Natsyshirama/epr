package com.epicerie.epr.service;

import com.epicerie.epr.model.AchatMp;

import java.time.LocalDate;
import java.util.List;

public interface AchatMatierePremierService {

    AchatMp createAchat(
            Long typeId,
            Long vendeurId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye
    );

    AchatMp updateAchat(
            Long id,
            Long typeId,
            Long vendeurId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye
    );

    List<AchatMp> getAll();

    AchatMp getById(Long id);

    void delete(Long id);
}