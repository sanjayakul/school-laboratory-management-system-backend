package com.laboratory.management.system.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PracticalRequestItemDto {
    private Long id;
    private String itemType;
    private String itemName;
    private BigDecimal requiredVolume;
    private String unit;
}
