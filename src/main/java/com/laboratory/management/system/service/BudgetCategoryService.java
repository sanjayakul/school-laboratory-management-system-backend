package com.laboratory.management.system.service;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.BudgetCategoryDto;
import com.laboratory.management.system.model.dto.BudgetCategorySummaryDto;

import java.util.List;

public interface BudgetCategoryService {

    BudgetCategoryDto createBudgetCategory(BudgetCategoryDto dto);

    // Get all budget categories
    List<BudgetCategoryDto> getAllBudgetCategories()throws DataNotFoundException;

    // Get budget summary for all categories
    //List<BudgetCategorySummaryDto> getBudgetCategorySummaries();
}
