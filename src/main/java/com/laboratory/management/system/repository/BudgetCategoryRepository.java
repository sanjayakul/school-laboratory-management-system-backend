package com.laboratory.management.system.repository;

import com.laboratory.management.system.model.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<BudgetCategory> findByNameIgnoreCase(String name);
}
