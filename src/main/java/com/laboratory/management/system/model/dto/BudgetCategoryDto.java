package com.laboratory.management.system.model.dto;

import lombok.Data;

@Data
public class BudgetCategoryDto {
    private Long id;
    private String name;
    private Double allocatedBudget;
    private String status;
}

