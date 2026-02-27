package com.epicerie.epr.service.typeMp;

import com.epicerie.epr.model.TypeMp;
import java.util.List;

public interface TypeMpService {
    TypeMp createTypeMp(String nom, String description);
    
    List<TypeMp> getAllTypeMps();
    
    TypeMp getTypeMpById(Long id);
    
    TypeMp updateTypeMp(Long id, String nom, String description);
    
    void deleteTypeMp(Long id);
}
