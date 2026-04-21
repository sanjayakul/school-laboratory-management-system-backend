package com.laboratory.management.system.repository.bkp;

import com.laboratory.management.system.model.bkp.BkpBudgetExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BkpBudgetExpenseRepository extends JpaRepository<BkpBudgetExpense, Long> {
}
