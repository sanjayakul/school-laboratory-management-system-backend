package com.laboratory.management.system.model.bkp;

import com.laboratory.management.system.model.Auditable;
import com.laboratory.management.system.model.BudgetCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bkp_budget_expense")
public class BkpBudgetExpense extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bkpExpenseId;

    @Column(name = "id", nullable = false)
    private Long budgetExpenseId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

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

    @Column(name = "bkp_last_modified_user", nullable = false)
    private String bkpLastModifiedUser;

    @Column(name = "bkp_last_modified_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bkpLastModifiedDate;
}
