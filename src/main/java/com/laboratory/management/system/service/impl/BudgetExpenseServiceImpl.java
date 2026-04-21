package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.BudgetCategory;
import com.laboratory.management.system.model.BudgetExpense;
import com.laboratory.management.system.model.bkp.BkpBudgetExpense;
import com.laboratory.management.system.model.dto.BudgetExpenseDto;
import com.laboratory.management.system.repository.BudgetCategoryRepository;
import com.laboratory.management.system.repository.BudgetExpenseRepository;
import com.laboratory.management.system.repository.bkp.BkpBudgetExpenseRepository;
import com.laboratory.management.system.service.BudgetExpenseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.laboratory.management.system.util.Enums.ACTIVE;

@Service
public class BudgetExpenseServiceImpl implements BudgetExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(BudgetExpenseServiceImpl.class);

    private final BudgetExpenseRepository budgetExpenseRepository;

    private final BudgetCategoryRepository categoryRepository;

    private final ModelMapper mapper;

    private final BkpBudgetExpenseRepository bkpBudgetExpenseRepository;

    public BudgetExpenseServiceImpl(BudgetExpenseRepository budgetExpenseRepository, BudgetCategoryRepository categoryRepository, ModelMapper mapper, BkpBudgetExpenseRepository bkpBudgetExpenseRepository) {
        this.budgetExpenseRepository = budgetExpenseRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.bkpBudgetExpenseRepository = bkpBudgetExpenseRepository;
    }


    @Override
    public BudgetExpenseDto createBudgetExpenses(BudgetExpenseDto dto) {

        logger.info("BudgetExpenseServiceImpl => createBudgetExpenses() => started!");

        try {

            BudgetExpense expense = mapper.map(dto, BudgetExpense.class);

            expense.setAmount(dto.getAmount());
            expense.setDescription(dto.getDescription());
            expense.setVendorSupplier(dto.getVendorSupplier());
            expense.setExpenseDate(dto.getExpenseDate());
            expense.setStatus(dto.getStatus());
            expense.setReceiptNumber(dto.getReceiptNumber());
            expense.setNotes(dto.getNotes());

            // Use categoryId if provided, else fallback to categoryName
            BudgetCategory category;
            if (dto.getCategoryId() != null) {
                category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found by ID: " + dto.getCategoryId()));
            } else if (dto.getCategoryName() != null && !dto.getCategoryName().isEmpty()) {
                category = categoryRepository.findAll().stream()
                        .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(dto.getCategoryName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Category not found by name: " + dto.getCategoryName()));
            } else {
                throw new RuntimeException("Category ID or Name must be provided");
            }
            expense.setCategory(category);
            expense.setCategoryName(category.getName());

            BudgetExpense savedEntity = budgetExpenseRepository.save(expense);

            logger.info("BudgetExpenseServiceImpl => createBudgetExpenses() => Finished!");

            return mapper.map(savedEntity, BudgetExpenseDto.class);

        } catch (Exception ex) {
            logger.error("Error in create BudgetExpense: ", ex);
            throw new RuntimeException("Failed to create BudgetExpense", ex);
        }
    }

    @Override
    public List<BudgetExpenseDto> getAllBudgetExpenses() throws DataNotFoundException {
        logger.info("BudgetExpenseServiceImpl => getAllBudgetExpenses() => started!");

        List<BudgetExpense> categories = budgetExpenseRepository.findAll();

        if (categories.isEmpty()) {
            throw new DataNotFoundException("No Budget Expenses found");
        }

        List<BudgetExpenseDto> budgetExpenseDtos = new ArrayList<>();

        for (BudgetExpense budgetExpense : categories) {
            BudgetExpenseDto expenseDto = new BudgetExpenseDto();
            BeanUtils.copyProperties(budgetExpense, expenseDto);
            budgetExpenseDtos.add(expenseDto);
        }

        return budgetExpenseDtos;
    }

    @Override
    public BudgetExpenseDto updateBudgetExpenses(Long id, BudgetExpenseDto budgetExpenseDto) throws DataNotFoundException {
        logger.info("BudgetExpenseServiceImpl -> updateBudgetExpenses() => started!");
        var existingValue = budgetExpenseRepository.findById(id);
        existingValue.orElseThrow(() -> new DataNotFoundException(String.format("Values not found for given budget expense Id - %s", id)));
        BudgetExpenseDto dto;
        BkpBudgetExpense bkpBudgetExpense = mapper.map(existingValue.get(), BkpBudgetExpense.class);
        bkpBudgetExpense.setBudgetExpenseId(existingValue.get().getId());
        bkpBudgetExpense.setBkpLastModifiedUser(existingValue.get().getModifiedUser());
        bkpBudgetExpense.setBkpLastModifiedDate(existingValue.get().getModifiedDate());
        bkpBudgetExpenseRepository.save(bkpBudgetExpense);

        BudgetExpense budgetExpense = mapper.map(budgetExpenseDto, BudgetExpense.class);
        budgetExpense.setId(existingValue.get().getId());
        budgetExpense.setCategory(existingValue.get().getCategory());
        budgetExpense.setCategoryName(existingValue.get().getCategoryName());
        budgetExpense.setStatus(ACTIVE.value());

        budgetExpense.setCreatedUser(existingValue.get().getCreatedUser());
        budgetExpense.setCreatedDate(existingValue.get().getCreatedDate());
        BudgetExpense updatedBudgetExpense = budgetExpenseRepository.save(budgetExpense);
        dto = mapper.map(updatedBudgetExpense, BudgetExpenseDto.class);


        logger.info("BudgetExpenseServiceImpl -> updateBudgetExpenses() => ended!");
        return dto;
    }
}
