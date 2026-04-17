package com.laboratory.management.system.model.dto;

import lombok.Data;

@Data
public class EquipmentInventoryDto {
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private Integer available;
    private String condition;
    private String location;
}

