package com.epicerie.epr.service.impl;

import com.epicerie.epr.model.*;
import com.epicerie.epr.repository.*;
import com.epicerie.epr.service.AchatMatierePremierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AchatMatierePremiereServiceImpl implements AchatMatierePremierService {

    private final AchatMpRepository achatRepository;
    private final TypeMpRepository typeRepository;
    private final VendeurRepository vendeurRepository;
    private final StatusPayementRepository statusRepository;

    @Override
    public AchatMp createAchat(
            Long typeId,
            Long vendeurId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye) {

        validateInputs(quantite, prixAchat);

        TypeMp type = typeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Type matière première introuvable"));

        Vendeur vendeur = vendeurRepository.findById(vendeurId)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));

        AchatMp achat = new AchatMp();

        achat.setType(type);
        achat.setVendeur(vendeur);
        achat.setQuantite(quantite);
        achat.setPrixAchat(prixAchat);
        achat.setDateAchat(dateAchat);
        achat.setSemaineDebut(semaineDebut);
        achat.setSemaineFin(semaineFin);
        achat.setMontantPaye(montantPaye);

        // Calcul automatique
        double total = quantite * prixAchat;
        achat.setPrixTotal(total);

        // Détermination automatique du status
        StatusPayment status = determinePaymentStatus(total, montantPaye);
        achat.setStatusPayment(status);

        return achatRepository.save(achat);
    }

    @Override
    public AchatMp updateAchat(
            Long id,
            Long typeId,
            Long vendeurId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye) {

        AchatMp achat = achatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achat introuvable"));

        validateInputs(quantite, prixAchat);

        TypeMp type = typeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Type matière première introuvable"));

        Vendeur vendeur = vendeurRepository.findById(vendeurId)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));

        achat.setType(type);
        achat.setVendeur(vendeur);
        achat.setQuantite(quantite);
        achat.setPrixAchat(prixAchat);
        achat.setDateAchat(dateAchat);
        achat.setSemaineDebut(semaineDebut);
        achat.setSemaineFin(semaineFin);
        achat.setMontantPaye(montantPaye);

        double total = quantite * prixAchat;
        achat.setPrixTotal(total);

        StatusPayment status = determinePaymentStatus(total, montantPaye);
        achat.setStatusPayment(status);

        return achatRepository.save(achat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AchatMp> getAll() {
        return achatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AchatMp getById(Long id) {
        return achatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achat introuvable"));
    }

    @Override
    public void delete(Long id) {
        if (!achatRepository.existsById(id)) {
            throw new RuntimeException("Achat introuvable");
        }
        achatRepository.deleteById(id);
    }

    // ==========================
    // MÉTHODES PRIVÉES
    // ==========================

    private void validateInputs(Double quantite, Double prixAchat) {
        if (quantite == null || quantite <= 0) {
            throw new RuntimeException("Quantité invalide");
        }
        if (prixAchat == null || prixAchat <= 0) {
            throw new RuntimeException("Prix d'achat invalide");
        }
    }

    private StatusPayment determinePaymentStatus(double total, Double montantPaye) {

        if (montantPaye == null || montantPaye == 0) {
            return statusRepository.findByNom("NON_PAYE")
                    .orElseThrow(() -> new RuntimeException("Status NON_PAYE non configuré"));
        }

        if (montantPaye >= total) {
            return statusRepository.findByNom("TOUT_PAYE")
                    .orElseThrow(() -> new RuntimeException("Status TOUT_PAYE non configuré"));
        }

        return statusRepository.findByNom("PARTIEL")
                .orElseThrow(() -> new RuntimeException("Status PARTIEL non configuré"));
    }
}