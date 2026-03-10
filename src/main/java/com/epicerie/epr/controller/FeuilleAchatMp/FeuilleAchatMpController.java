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

    @PostMapping("/create/{vendeur_id}")
    public ResponseEntity<FeuilleAchatMp> createFeuille(@PathVariable Long vendeur_id) {

        FeuilleAchatMp feuille = service.createFeuille(vendeur_id );

        return ResponseEntity.ok(feuille);
    }

    @PostMapping("/{feuilleId}/achat")
    public ResponseEntity<AchatMp> addAchat(
            @PathVariable Long feuilleId,
            @RequestBody AchatRequest request) {

        AchatMp achat = service.addAchatToFeuille(
                feuilleId,
                request.getTypeId(),
                request.getQuantite(),
                request.getPrixAchat(),
                request.getDateAchat(),
                request.getSemaineDebut(),
                request.getSemaineFin(),
                request.getMontantPaye()
        );

        return ResponseEntity.ok(achat);
    }
    
    //valider une feuille d'achat 
    @PostMapping("/{feuilleId}/valider") 
    public ResponseEntity<FeuilleAchatMp> valider(
            @PathVariable Long feuilleId) {

        FeuilleAchatMp feuille = service.validerFeuille(feuilleId);

        return ResponseEntity.ok(feuille);
    }

    // Endpoint pour payer une feuille d'achat  
    @PostMapping("/payeFeuille/{feuilleId}")
    public ResponseEntity<FeuilleAchatMp> payerFeuille(
            @PathVariable Long feuilleId,
            @RequestParam Double montant) {

        return ResponseEntity.ok(service.payerFeuille(feuilleId, montant));
    }

    @PostMapping("/payeVendeur/{vendeurId}")
    public ResponseEntity<String> payerVendeur(
        @PathVariable Long vendeurId,
        @RequestParam Double montant) {

    service.payerVendeur(vendeurId, montant);

    return ResponseEntity.ok("Paiement effectué");
}
    
    @GetMapping("/restant-feuille/{feuilleId}")
    public Double getRestantFeuille(@PathVariable Long feuilleId) {

        return service.getMontantRestantFeuille(feuilleId);
    }

    @GetMapping("/restant-vendeur/{vendeurId}")
    public Double getRestantVendeur(@PathVariable Long vendeurId) {

        return service.getMontantRestantVendeur(vendeurId);
    }
}