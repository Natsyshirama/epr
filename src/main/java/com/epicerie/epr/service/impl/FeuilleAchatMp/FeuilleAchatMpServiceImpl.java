package com.epicerie.epr.service.impl.FeuilleAchatMp;


import com.epicerie.epr.model.*;
import com.epicerie.epr.repository.*;
import com.epicerie.epr.service.feuilleachatmp.FeuillerAchatMpService;
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
    public FeuilleAchatMp createFeuille() {

        FeuilleAchatMp feuille = new FeuilleAchatMp();

        feuille.setNumero(generateNumeroFeuille());
        feuille.setDateCreation(LocalDate.now());

        return feuilleAchatMpRepository.save(feuille);
    }

    @Override
    public AchatMp addAchatToFeuille(
            Long feuilleId,
            Long typeId,
            Long vendeurId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye) {

        FeuilleAchatMp feuille = feuilleAchatMpRepository.findById(feuilleId)
                .orElseThrow(() -> new RuntimeException("Feuille introuvable"));

        TypeMp type = typeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Type introuvable"));

        Vendeur vendeur = vendeurRepository.findById(vendeurId)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));

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

        StatusPayment status = determinePaymentStatus(total, montantPaye);
        achat.setStatusPayment(status);

        return achatRepository.save(achat);
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


}
