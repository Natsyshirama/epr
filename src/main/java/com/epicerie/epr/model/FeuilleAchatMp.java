package com.epicerie.epr.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "feuille_achat_mp")
public class FeuilleAchatMp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private LocalDate dateCreation;

    private Boolean valide = false;

    private Double montantTotal;

    private Double montantRestant;
    
    @ManyToOne
    @JoinColumn(name = "vendeur_id", nullable = false)
    private Vendeur vendeur;

    @OneToMany(mappedBy = "feuilleAchatMp", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AchatMp> achats;



}
