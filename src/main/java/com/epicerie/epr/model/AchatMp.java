package com.epicerie.epr.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "achatMp")
public class AchatMp {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TypeMp type;

    @ManyToOne
    @JoinColumn(name = "vendeur_id", nullable = false)
    private Vendeur vendeur;
    
    @ManyToOne
    @JoinColumn(name = "feuille_id")
    private FeuilleAchatMp feuilleAchatMp;

    private Double quantite;
    private Double prixAchat;

    private Double prixTotal;

    private LocalDate dateAchat;

    private LocalDate semaineDebut;
    private LocalDate semaineFin;

    private Double montantReste;

    @ManyToOne
    @JoinColumn(name = "status_payment_id")
    private StatusPayment statusPayment;

    private Double montantPaye;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMp getType() {
        return type;
    }

    public void setType(TypeMp type) {
        this.type = type;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public LocalDate getSemaineDebut() {
        return semaineDebut;
    }

    public void setSemaineDebut(LocalDate semaineDebut) {
        this.semaineDebut = semaineDebut;
    }

    public LocalDate getSemaineFin() {
        return semaineFin;
    }

    public void setSemaineFin(LocalDate semaineFin) {
        this.semaineFin = semaineFin;
    }

    public StatusPayment getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(StatusPayment statusPayment) {
        this.statusPayment = statusPayment;
    }

    public Double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public Double getMontantReste() {
        return montantReste;
    }

    public void setMontantReste(Double montantReste) {
        this.montantReste = montantReste;
    }

    @PrePersist
    @PreUpdate
    public void calculTotal() {
        if (quantite != null && prixAchat != null) {
            this.prixTotal = this.quantite * this.prixAchat;
        }
    }
}
