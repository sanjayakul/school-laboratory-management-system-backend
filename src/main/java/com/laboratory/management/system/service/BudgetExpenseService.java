package com.laboratory.management.system.service;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.BudgetExpenseDto;

import java.util.List;

public interface BudgetExpenseService {

    BudgetExpenseDto createBudgetExpenses(BudgetExpenseDto dto);

    List<BudgetExpenseDto> getAllBudgetExpenses()throws DataNotFoundException;

    BudgetExpenseDto updateBudgetExpenses(Long id, BudgetExpenseDto budgetExpenseDto) throws DataNotFoundException;
}
