package com.laboratory.management.system.model.dto;

import lombok.Data;

@Data
public class BudgetCategorySummaryDto {
    private Long id;
    private String name;
    private Double allocatedBudget;
    private Double spentAmount;
    private Double remainingAmount;
    private String status;
}

