package com.epicerie.epr.service.stockService;

import java.time.LocalDate;

import com.epicerie.epr.model.TypeMp;

public interface StockMpService {
    void updateStockAfterAchat(
            TypeMp typeMp,
            Double quantite,
            Double total,
            Double montantPaye,
            Double montantReste,
            LocalDate dateAchat
    );
}
