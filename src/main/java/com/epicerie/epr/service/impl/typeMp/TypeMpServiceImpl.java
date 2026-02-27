package com.epicerie.epr.service.impl.typeMp;

import com.epicerie.epr.model.TypeMp;
import com.epicerie.epr.repository.TypeMpRepository;
import com.epicerie.epr.service.typeMp.TypeMpService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeMpServiceImpl implements TypeMpService {
    private final TypeMpRepository typeMpRepository;

    @Override
    public TypeMp createTypeMp(String nom, String code) {
        TypeMp typeMp = new TypeMp();
        typeMp.setNom(nom);
        typeMp.setCode(code);
        return typeMpRepository.save(typeMp);
    }

    @Override
    public List<TypeMp> getAllTypeMps() {
        return typeMpRepository.findAll();
    }

    @Override
    public TypeMp getTypeMpById(Long id) {
        return typeMpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeMp introuvable"));
    }

    @Override
    public TypeMp updateTypeMp(Long id, String nom, String code) {
        TypeMp typeMp = typeMpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeMp introuvable"));
        typeMp.setNom(nom);
        typeMp.setCode(code);
        return typeMpRepository.save(typeMp);
    }

    @Override
    public void deleteTypeMp(Long id) {
        if (!typeMpRepository.existsById(id)) {
            throw new RuntimeException("TypeMp introuvable");
        }
        typeMpRepository.deleteById(id);
    }
}