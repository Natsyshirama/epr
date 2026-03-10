package com.epicerie.epr.service.impl.FeuilleAchatMp;


import com.epicerie.epr.model.*;
import com.epicerie.epr.repository.*;
import com.epicerie.epr.service.feuilleachatmp.FeuillerAchatMpService;
import com.epicerie.epr.service.stockService.StockMpService;

import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeuilleAchatMpServiceImpl implements FeuillerAchatMpService {
    
    private final FeuilleAchatMpRepository feuilleAchatMpRepository;
    private final AchatMpRepository achatRepository;
    private final TypeMpRepository typeRepository;
    private final VendeurRepository vendeurRepository;
    private final StatusPayementRepository statusRepository;
    
    private final StockMpService stockMpService;

     @Override
    @Transactional(readOnly = true)
    public List<FeuilleAchatMp> getFeuillesByVendeurId(Long vendeurId) {
        Vendeur vendeur = vendeurRepository.findById(vendeurId)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));

        // Récupère toutes les feuilles qui contiennent au moins un achat de ce vendeur
        return feuilleAchatMpRepository.findDistinctByAchatsVendeurId(vendeurId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AchatMp> getAchatsByFeuilleId(Long feuilleId) {
        FeuilleAchatMp feuille = feuilleAchatMpRepository.findById(feuilleId)
                .orElseThrow(() -> new RuntimeException("Feuille introuvable"));

        
            return achatRepository.findByFeuilleAchatMpId(feuilleId);

    }

    @Override
    public FeuilleAchatMp createFeuille(Long vendeur_id) {

        FeuilleAchatMp feuille = new FeuilleAchatMp();

        feuille.setNumero(generateNumeroFeuille());
        feuille.setDateCreation(LocalDate.now());

        Vendeur vendeur = vendeurRepository.findById(vendeur_id)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));

        feuille.setVendeur(vendeur);
        
        return feuilleAchatMpRepository.save(feuille);
    }

    @Override
    public AchatMp addAchatToFeuille(
            Long feuilleId,
            Long typeId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye) {
               

        FeuilleAchatMp feuille = feuilleAchatMpRepository.findById(feuilleId)
                .orElseThrow(() -> new RuntimeException("Feuille introuvable"));
        if (feuille.getValide()) {
            throw new RuntimeException("Impossible d'ajouter un achat à une feuille validée");
        }

        validateInputs(quantite, prixAchat, montantPaye);

        TypeMp type = typeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Type introuvable"));
        
        
        Vendeur vendeur = feuille.getVendeur();

        if (vendeur == null) {
            throw new RuntimeException("La feuille n'a pas de vendeur");
            }

        
        AchatMp achat = new AchatMp();

        achat.setFeuilleAchatMp(feuille);
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

      
        if (montantPaye > total) {
            throw new RuntimeException("Montant payé supérieur au total");
        }

        double restant = total - montantPaye;
        achat.setMontantReste(restant);

        StatusPayment status = determinePaymentStatus(total, montantPaye);
        achat.setStatusPayment(status);
        
        stockMpService.updateStockAfterAchat(
        type,
        quantite,
        total,
        montantPaye,
        restant,
        dateAchat
                    );
        
        if (feuille.getMontantTotal() == null) {
    feuille.setMontantTotal(0.0);
}

if (feuille.getMontantRestant() == null) {
    feuille.setMontantRestant(0.0);
}

        feuille.setMontantTotal(feuille.getMontantTotal() + total);
        feuille.setMontantRestant( feuille.getMontantRestant() + restant);
        return achatRepository.save(achat);

    }

    @Override
    @Transactional
    public FeuilleAchatMp payerFeuille(Long feuilleId, Double montant) {

    FeuilleAchatMp feuille = feuilleAchatMpRepository.findById(feuilleId)
            .orElseThrow(() -> new RuntimeException("Feuille introuvable"));

    if (montant > feuille.getMontantRestant()) {
        throw new RuntimeException("Montant supérieur au restant");
    }

    feuille.setMontantRestant(feuille.getMontantRestant() - montant);

    return feuilleAchatMpRepository.save(feuille);
    }

    @Override
    @Transactional
    public void payerVendeur(Long vendeurId, Double montant) {

    List<FeuilleAchatMp> feuilles =
            feuilleAchatMpRepository.findByVendeurIdAndValideTrueOrderByDateCreationAsc(vendeurId);

    if (feuilles.isEmpty()) {
        throw new RuntimeException("Aucune feuille validée trouvée pour ce vendeur");
    }

    double montantRestant = montant;

    for (FeuilleAchatMp feuille : feuilles) {

        if (montantRestant <= 0) {
            break;
        }

        double restantFeuille = feuille.getMontantRestant();

        if (restantFeuille <= montantRestant) {

            feuille.setMontantRestant(0.0);
            montantRestant -= restantFeuille;

        } else {

            feuille.setMontantRestant(restantFeuille - montantRestant);
            montantRestant = 0;
        }

        feuilleAchatMpRepository.save(feuille);
    }
}


    @Override
    public FeuilleAchatMp validerFeuille(Long feuilleId) {

        FeuilleAchatMp feuille = feuilleAchatMpRepository.findById(feuilleId)
                .orElseThrow(() -> new RuntimeException("Feuille introuvable"));

        feuille.setValide(true);

        return feuilleAchatMpRepository.save(feuille);
    }

    private String generateNumeroFeuille() {

        FeuilleAchatMp last = feuilleAchatMpRepository
                .findTopByOrderByIdDesc()
                .orElse(null);

        int next = 1;

        if (last != null) {
            String lastNumero = last.getNumero().replace("FMP-", "");
            next = Integer.parseInt(lastNumero) + 1;
        }

        return String.format("FMP-%04d", next);
    }

    private StatusPayment determinePaymentStatus(double total, Double montantPaye) {

        if (montantPaye == null || montantPaye == 0) {
            return statusRepository.findByNom("NON_PAYE")
                    .orElseThrow();
        }

        if (montantPaye >= total) {
            return statusRepository.findByNom("TOUT_PAYE")
                    .orElseThrow();
        }

        return statusRepository.findByNom("PARTIEL")
                .orElseThrow();
    }
    

    private void validateInputs(Double quantite, Double prixAchat, Double montantPaye) {
        if (quantite == null || quantite <= 0) {
            throw new RuntimeException("Quantité invalide");
        }
        if (prixAchat == null || prixAchat <= 0) {
            throw new RuntimeException("Prix d'achat invalide");
        }
        if (montantPaye != null && montantPaye < 0) {
            throw new RuntimeException("Montant payé invalide");
        }
    }


}
