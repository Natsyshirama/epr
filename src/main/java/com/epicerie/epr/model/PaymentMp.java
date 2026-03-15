package com.epicerie.epr.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDate;

import lombok.Data;

@Data
@Entity
@Table(name = "payment_mp")
public class PaymentMp {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name= "feuille_achat_mp_id")
    private FeuilleAchatMp feuilleAchatMp;

}
