package com.epicerie.epr.controller.typeMp;

import com.epicerie.epr.dto.TypeMpRequest;
import com.epicerie.epr.model.TypeMp;
import com.epicerie.epr.service.typeMp.TypeMpService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/type-mps")
@RequiredArgsConstructor
public class TypeMpController {
    private final TypeMpService typeMpService;

    @PostMapping
    public ResponseEntity<TypeMp> createTypeMp(@RequestBody TypeMpRequest request) {
        TypeMp typeMp = typeMpService.createTypeMp(request.getNom(), request.getCode());
        return ResponseEntity.ok(typeMp);
    }

    @GetMapping
    public ResponseEntity<List<TypeMp>> getAllTypeMps() {
        List<TypeMp> typeMps = typeMpService.getAllTypeMps();
        return ResponseEntity.ok(typeMps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeMp> getTypeMpById(
        @PathVariable Long id) {
        TypeMp typeMp = typeMpService.getTypeMpById(id);
        return ResponseEntity.ok(typeMp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeMp> updateTypeMp(
        @PathVariable Long id, 
        @RequestBody TypeMpRequest request) {
        TypeMp updatedTypeMp = typeMpService.updateTypeMp(id, request.getNom(), request.getCode());
        return ResponseEntity.ok(updatedTypeMp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeMp(
        @PathVariable Long id) {
        typeMpService.deleteTypeMp(id);
        return ResponseEntity.noContent().build();
    }
}