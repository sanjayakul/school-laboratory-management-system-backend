package com.laboratory.management.system.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BudgetExpenseDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private Double amount;
    private String description  ;
    private String vendorSupplier;
    private LocalDate expenseDate;
    private String status;
    private String receiptNumber;
    private String notes;
}

