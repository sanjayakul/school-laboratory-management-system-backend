package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.BudgetCategory;
import com.laboratory.management.system.model.dto.BudgetCategoryDto;
import com.laboratory.management.system.model.dto.BudgetCategorySummaryDto;
import com.laboratory.management.system.repository.BudgetCategoryRepository;
import com.laboratory.management.system.service.BudgetCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.laboratory.management.system.util.Enums.ACTIVE;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetCategoryServiceImpl implements BudgetCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(BudgetCategoryServiceImpl.class);

    private final BudgetCategoryRepository budgetCategoryRepository;
    private final ModelMapper mapper;

    public BudgetCategoryServiceImpl(BudgetCategoryRepository budgetCategoryRepository, ModelMapper mapper) {
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public BudgetCategoryDto createBudgetCategory(BudgetCategoryDto dto) {
        logger.info("BudgetCategoryServiceImpl => createBudgetCategory() => started!");
        try {
            if (budgetCategoryRepository.existsByNameIgnoreCase(dto.getName())) {
                throw new IllegalArgumentException("Budget category name already exists");
            }
            BudgetCategory entity = mapper.map(dto, BudgetCategory.class);
            entity.setName(dto.getName());
            entity.setAllocatedBudget(dto.getAllocatedBudget());

            entity.setStatus(ACTIVE.value());

            BudgetCategory savedEntity = budgetCategoryRepository.save(entity);
            logger.info("BudgetCategoryServiceImpl => createBudgetCategory() => Finished!");
            return mapper.map(savedEntity, BudgetCategoryDto.class);
        } catch (Exception ex) {
            logger.error("Error in create Budget Category: ", ex);
            throw new RuntimeException("Failed to create Budget Category", ex);
        }
    }

    @Override
    public List<BudgetCategoryDto> getAllBudgetCategories() throws DataNotFoundException {
        logger.info("BudgetCategoryServiceImpl => getAllBudgetCategories() => started!");

        List<BudgetCategory> categories = budgetCategoryRepository.findAll();

        if (categories == null || categories.isEmpty()) {
            throw new DataNotFoundException("No Budget Categories found");
        }

        List<BudgetCategoryDto> categoryDtos = new ArrayList<>();

        for (BudgetCategory budgetCategory : categories) {
            BudgetCategoryDto categoryDto = new BudgetCategoryDto();
            BeanUtils.copyProperties(budgetCategory, categoryDto);
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<BudgetCategorySummaryDto> getBudgetCategorySummaries() {
        List<BudgetCategory> categories = budgetCategoryRepository.findAll();
        List<BudgetCategorySummaryDto> summaries = categories.stream().map(category -> {
            BudgetCategorySummaryDto dto = new BudgetCategorySummaryDto();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setAllocatedBudget(category.getAllocatedBudget());
            dto.setStatus(category.getStatus());
            double spent = budgetTransactionRepository.findByBudgetCategoryId(category.getId())
                    .stream()
                    .filter(t -> "EXPENDITURE".equalsIgnoreCase(t.getTransactionType()))
                    .mapToDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                    .sum();
            dto.setSpentAmount(spent);
            dto.setRemainingAmount(category.getAllocatedBudget() - spent);
            return dto;
        }).toList();
        return summaries;
    }*/
}
