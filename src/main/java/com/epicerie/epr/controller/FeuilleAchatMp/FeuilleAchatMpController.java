package com.epicerie.epr.controller.FeuilleAchatMp;

import com.epicerie.epr.dto.AchatRequest;
import com.epicerie.epr.model.*;

import com.epicerie.epr.service.feuilleachatmp.FeuillerAchatMpService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 import java.util.List;

@RestController
@RequestMapping("/api/feuille-achat-mp")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FeuilleAchatMpController  {
    private final FeuillerAchatMpService service;

    // Liste des feuilles par vendeur
    
    @GetMapping("/vendeur/{vendeurId}")
    public ResponseEntity<List<FeuilleAchatMp>> getFeuillesByVendeur(@PathVariable Long vendeurId) {
        List<FeuilleAchatMp> feuilles = service.getFeuillesByVendeurId(vendeurId);
        return ResponseEntity.ok(feuilles);
    }

    // Liste des achats par feuille
    @GetMapping("/{feuilleId}/achats")
    public ResponseEntity<List<AchatMp>> getAchatsByFeuille(@PathVariable Long feuilleId) {
        List<AchatMp> achats = service.getAchatsByFeuilleId(feuilleId);
        return ResponseEntity.ok(achats);
    }

    @PostMapping
    public ResponseEntity<FeuilleAchatMp> createFeuille() {

        FeuilleAchatMp feuille = service.createFeuille();

        return ResponseEntity.ok(feuille);
    }

    @PostMapping("/{feuilleId}/achat")
    public ResponseEntity<AchatMp> addAchat(
            @PathVariable Long feuilleId,
            @RequestBody AchatRequest request) {

        AchatMp achat = service.addAchatToFeuille(
                feuilleId,
                request.getTypeId(),
                request.getVendeurId(),
                request.getQuantite(),
                request.getPrixAchat(),
                request.getDateAchat(),
                request.getSemaineDebut(),
                request.getSemaineFin(),
                request.getMontantPaye()
        );

        return ResponseEntity.ok(achat);
    }
    
     @PostMapping("/{feuilleId}/valider")
    public ResponseEntity<FeuilleAchatMp> valider(
            @PathVariable Long feuilleId) {

        FeuilleAchatMp feuille = service.validerFeuille(feuilleId);

        return ResponseEntity.ok(feuille);
    }
    
}