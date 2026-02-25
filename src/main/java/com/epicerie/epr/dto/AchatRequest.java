package com.epicerie.epr.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AchatRequest {

    private Long typeId;
    private Long vendeurId;
    private Double quantite;
    private Double prixAchat;
    private LocalDate dateAchat;
    private LocalDate semaineDebut;
    private LocalDate semaineFin;
    private Double montantPaye;
}