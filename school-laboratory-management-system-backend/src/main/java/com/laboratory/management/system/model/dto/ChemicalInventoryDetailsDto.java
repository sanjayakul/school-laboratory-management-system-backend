package com.laboratory.management.system.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ChemicalInventoryDetailsDto {

    private String chemicalName;
    private String chemicalFormula;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal minStockLevel;
    private String hazardLevel;
    private String storageLocation;
    private LocalDate expirationDate;
    private String casNumber;
    private String supplier;
    private String safetyNotes;
    private String status;
}
