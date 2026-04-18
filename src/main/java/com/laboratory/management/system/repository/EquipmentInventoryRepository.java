package com.laboratory.management.system.repository;

import com.laboratory.management.system.model.EquipmentInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentInventoryRepository extends JpaRepository<EquipmentInventory, Long>, JpaSpecificationExecutor<EquipmentInventory> {
}
