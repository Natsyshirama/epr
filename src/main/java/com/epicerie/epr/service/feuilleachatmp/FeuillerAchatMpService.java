package com.epicerie.epr.service.feuilleachatmp;


import com.epicerie.epr.model.AchatMp;
import com.epicerie.epr.model.FeuilleAchatMp;

import java.time.LocalDate;
import java.util.List;

public interface FeuillerAchatMpService {

    List<FeuilleAchatMp> getFeuillesByVendeurId(Long vendeurId);

    List<AchatMp> getAchatsByFeuilleId(Long feuilleId);


    FeuilleAchatMp createFeuille();

    
    AchatMp addAchatToFeuille(
            Long feuilleId,
            Long typeId,
            Long vendeurId,
            Double quantite,
            Double prixAchat,
            LocalDate dateAchat,
            LocalDate semaineDebut,
            LocalDate semaineFin,
            Double montantPaye
    );

    FeuilleAchatMp validerFeuille(Long feuilleId);
}
