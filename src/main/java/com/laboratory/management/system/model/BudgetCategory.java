package com.laboratory.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "budget_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCategory extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    @Column(name = "allocated_budget", nullable = false)
    private Double allocatedBudget;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
}

