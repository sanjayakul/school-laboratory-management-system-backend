package com.laboratory.management.system.service;

import com.laboratory.management.system.model.dto.EquipmentInventoryDto;
import com.laboratory.management.system.service.criteria.EquipmentInventoryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentInventoryService {
    EquipmentInventoryDto createEquipment(EquipmentInventoryDto dto);

    EquipmentInventoryDto updateEquipment(Long id, EquipmentInventoryDto dto);

    void deleteEquipment(Long id);

    List<EquipmentInventoryDto> getAllEquipment();

    EquipmentInventoryDto getEquipmentById(Long id);

    Page<EquipmentInventoryDto> findEquipmentByFilters(EquipmentInventoryCriteria criteria, Pageable pageable);
}
