package com.epicerie.epr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.Data;

@Data
@Entity
@Table(name = "stock_mp")
public class StockMp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "type_mp_id")
    private TypeMp typeMp;

    private Double quantiteStock;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Double valeurStock;

    private Double totalPaye;

    private Double totalImpaye;
}
