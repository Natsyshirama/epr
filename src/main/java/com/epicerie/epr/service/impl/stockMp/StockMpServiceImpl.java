package com.epicerie.epr.service.impl.stockMp;

import org.springframework.stereotype.Service;

import com.epicerie.epr.model.*;
import com.epicerie.epr.repository.*;
import com.epicerie.epr.service.stockService.StockMpService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMpServiceImpl implements StockMpService {
    
    private final StockMpRepository stockMpRepository;

    @Override
    public void updateStockAfterAchat(
            TypeMp typeMp,
            Double quantite,
            Double total,
            Double montantPaye,
            Double montantReste,
            java.time.LocalDate dateAchat
    ) {
        StockMp stock = stockMpRepository.findByTypeMp(typeMp)
                .orElseGet(() -> {
                    StockMp newStock = new StockMp();
                    newStock.setTypeMp(typeMp);
                    newStock.setQuantiteStock(0.0);
                    newStock.setValeurStock(0.0);
                    newStock.setTotalPaye(0.0);
                    newStock.setTotalImpaye(0.0);
                    return newStock;
                });

        // Met à jour les quantités et les valeurs
        stock.setQuantiteStock(stock.getQuantiteStock() + quantite);
        stock.setValeurStock(stock.getValeurStock() + total);
        stock.setTotalPaye(stock.getTotalPaye() + montantPaye);
        stock.setTotalImpaye(stock.getTotalImpaye() + montantReste);

        // Met à jour les dates de début et de fin
        if (stock.getDateDebut() == null || dateAchat.isBefore(stock.getDateDebut())) {
            stock.setDateDebut(dateAchat);
        }
        if (stock.getDateFin() == null || dateAchat.isAfter(stock.getDateFin())) {
            stock.setDateFin(dateAchat);
        }

        stockMpRepository.save(stock);
    }
}
