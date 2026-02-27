package com.epicerie.epr.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class VendeurRequest {
    private String nom;
    private String phone;
    private String info;
    private LocalDate dateCreated;

}
