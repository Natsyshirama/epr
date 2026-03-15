package com.epicerie.epr.repository;
import com.epicerie.epr.model.PaymentMp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMpRepository extends JpaRepository<PaymentMp, Long> {
    
}
