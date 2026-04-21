package com.laboratory.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "budget_expense")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetExpense extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private BudgetCategory category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "category_name", length = 500)
    private String categoryName;

    @Column(name = "vendor_supplier", length = 255)
    private String vendorSupplier;

    @Column(name = "date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "receipt_number", length = 100)
    private String receiptNumber;

    @Column(name = "notes", length = 1000)
    private String notes;
}

