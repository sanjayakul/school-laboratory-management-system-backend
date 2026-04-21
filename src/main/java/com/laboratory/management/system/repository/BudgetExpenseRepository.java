package com.laboratory.management.system.repository;

import com.laboratory.management.system.model.BudgetExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetExpenseRepository extends JpaRepository<BudgetExpense, Long> {
}

