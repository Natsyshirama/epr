package com.epicerie.epr.controller;

import com.epicerie.epr.dto.AchatRequest;
import com.epicerie.epr.model.AchatMp;
import com.epicerie.epr.service.AchatMatierePremierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Cette classe reçoit des requêtes HTTP et retourne du JSON 
@RequestMapping("/api/achat-mp")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AchatMatierePremiereController {

    private final AchatMatierePremierService service;

    // ==========================
    // CREATE
    // ==========================
    @PostMapping
    public ResponseEntity<AchatMp> create(
            @RequestBody AchatRequest request) {

        AchatMp achat = service.createAchat(
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

    // ==========================
    // UPDATE
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<AchatMp> update(
            @PathVariable Long id,
            @RequestBody AchatRequest request) {

        AchatMp achat = service.updateAchat(
                id,
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

    // ==========================
    // GET ALL
    // ==========================
    @GetMapping
    public ResponseEntity<List<AchatMp>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    // ==========================
    // GET BY ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<AchatMp> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // ==========================
    // DELETE
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}