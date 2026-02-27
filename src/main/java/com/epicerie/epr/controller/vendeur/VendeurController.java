package com.epicerie.epr.controller.vendeur;

import com.epicerie.epr.dto.VendeurRequest;
import com.epicerie.epr.model.Vendeur;
import com.epicerie.epr.service.vendeur.VendeurService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendeurs")
@RequiredArgsConstructor
public class VendeurController {
    private final VendeurService vendeurService;

    @PostMapping
    public ResponseEntity<Vendeur> createVendeur(@RequestBody VendeurRequest request) {
        Vendeur vendeur = vendeurService.createVendeur(
                request.getNom(),
                request.getPhone(),
                request.getInfo(),
                request.getDateCreated()
        );
        return ResponseEntity.ok(vendeur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendeur> updateVendeur(
            @PathVariable Long id,
            @RequestBody VendeurRequest request) {

        Vendeur vendeur = vendeurService.updateVendeur(
                id,
                request.getNom(),
                request.getPhone(),
                request.getInfo(),
                request.getDateCreated()
        );
        return ResponseEntity.ok(vendeur);
    }

    @GetMapping
    public ResponseEntity<List<Vendeur>> getAllVendeurs() {
        return ResponseEntity.ok(vendeurService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendeur> getVendeurById(@PathVariable Long id) {
        return ResponseEntity.ok(vendeurService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendeur(@PathVariable Long id) {
        vendeurService.deleteVendeur(id);
        return ResponseEntity.noContent().build();
    }
}
