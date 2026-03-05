package com.epicerie.epr.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
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

    @OneToMany(mappedBy = "feuilleAchatMp", cascade = CascadeType.ALL)
    private List<AchatMp> achats;



}
