package com.epicerie.epr.service.impl.vendeur;

import com.epicerie.epr.model.Vendeur;
import com.epicerie.epr.service.vendeur.VendeurService;
import com.epicerie.epr.repository.VendeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class VendeurServiceImpl implements VendeurService {
    private final VendeurRepository vendeurRepository;

    @Override
    public Vendeur createVendeur(String nom, String phone, String info, java.time.LocalDate dateCreated) {
        Vendeur vendeur = new Vendeur();
        vendeur.setNom(nom);
        vendeur.setPhone(phone);
        vendeur.setInfo(info);
        vendeur.setDateCreated(dateCreated);
        return vendeurRepository.save(vendeur);
    }

    @Override
    public Vendeur updateVendeur(Long id, String nom, String phone, String info, java.time.LocalDate dateCreated) {
        Vendeur vendeur = vendeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));
        vendeur.setNom(nom);
        vendeur.setPhone(phone);
        vendeur.setInfo(info);
        vendeur.setDateCreated(dateCreated);
        return vendeurRepository.save(vendeur);
    }

    @Override
    public List<Vendeur> getAll() {
        return vendeurRepository.findAll();
    }

    @Override
    public Vendeur getById(Long id) {
        return vendeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));
    }

    @Override
    public void deleteVendeur(Long id) {
        if (!vendeurRepository.existsById(id)) {
            throw new RuntimeException("Vendeur introuvable");
        }
        vendeurRepository.deleteById(id);
    }

}
