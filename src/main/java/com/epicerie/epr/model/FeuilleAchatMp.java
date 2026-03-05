package com.epicerie.epr.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "feuille_achat_mp")
public class FeuilleAchatMp {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private LocalDate dateCreation;


    @OneToMany(mappedBy = "feuilleAchatMp", cascade = CascadeType.ALL)
    private List<AchatMp> achats;

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

}
