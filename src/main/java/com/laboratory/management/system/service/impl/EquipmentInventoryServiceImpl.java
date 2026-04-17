<<<<<<< HEAD
package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.model.EquipmentInventory;
import com.laboratory.management.system.model.dto.EquipmentInventoryDto;
import com.laboratory.management.system.repository.EquipmentInventoryRepository;
import com.laboratory.management.system.service.EquipmentInventoryService;
import com.laboratory.management.system.service.criteria.EquipmentInventoryCriteria;
import com.laboratory.management.system.service.specification.EquipmentInventorySpecification;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentInventoryServiceImpl implements EquipmentInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(EquipmentInventoryServiceImpl.class);
    private final EquipmentInventoryRepository repository;
    private final ModelMapper mapper;

    public EquipmentInventoryServiceImpl(EquipmentInventoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EquipmentInventoryDto createEquipment(EquipmentInventoryDto dto) {
        logger.info("Creating equipment: {}", dto.getName());
        EquipmentInventory entity = mapper.map(dto, EquipmentInventory.class);
        EquipmentInventory saved = repository.save(entity);
        return mapper.map(saved, EquipmentInventoryDto.class);
    }

    @Override
    public EquipmentInventoryDto updateEquipment(Long id, EquipmentInventoryDto dto) {
        logger.info("Updating equipment id: {}", id);
        EquipmentInventory entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setQuantity(dto.getQuantity());
        entity.setAvailable(dto.getAvailable());
        entity.setCondition(dto.getCondition());
        entity.setLocation(dto.getLocation());
        EquipmentInventory saved = repository.save(entity);
        return mapper.map(saved, EquipmentInventoryDto.class);
    }

    @Override
    public void deleteEquipment(Long id) {
        logger.info("Deleting equipment id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public List<EquipmentInventoryDto> getAllEquipment() {
        logger.info("Fetching all equipment");
        return repository.findAll().stream()
                .map(e -> mapper.map(e, EquipmentInventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentInventoryDto getEquipmentById(Long id) {
        logger.info("Fetching equipment by id: {}", id);
        EquipmentInventory entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        return mapper.map(entity, EquipmentInventoryDto.class);
    }

    @Override
    public Page<EquipmentInventoryDto> findEquipmentByFilters(EquipmentInventoryCriteria criteria, Pageable pageable) {
        logger.info("Filtering equipment inventory");
        Specification<EquipmentInventory> spec = Specification
                .where(EquipmentInventorySpecification.matchName(criteria.getName()))
                .and(EquipmentInventorySpecification.matchCategory(criteria.getCategory()))
                .and(EquipmentInventorySpecification.matchCondition(criteria.getCondition()))
                .and(EquipmentInventorySpecification.matchLocation(criteria.getLocation()));
        Page<EquipmentInventory> page = repository.findAll(spec, pageable);
        return page.map(e -> mapper.map(e, EquipmentInventoryDto.class));
    }
}
=======
package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.model.EquipmentInventory;
import com.laboratory.management.system.model.dto.EquipmentInventoryDto;
import com.laboratory.management.system.repository.EquipmentInventoryRepository;
import com.laboratory.management.system.service.EquipmentInventoryService;
import com.laboratory.management.system.service.criteria.EquipmentInventoryCriteria;
import com.laboratory.management.system.service.specification.EquipmentInventorySpecification;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentInventoryServiceImpl implements EquipmentInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(EquipmentInventoryServiceImpl.class);
    private final EquipmentInventoryRepository repository;
    private final ModelMapper mapper;

    public EquipmentInventoryServiceImpl(EquipmentInventoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EquipmentInventoryDto createEquipment(EquipmentInventoryDto dto) {
        logger.info("Creating equipment: {}", dto.getName());
        EquipmentInventory entity = mapper.map(dto, EquipmentInventory.class);
        EquipmentInventory saved = repository.save(entity);
        return mapper.map(saved, EquipmentInventoryDto.class);
    }

    @Override
    public EquipmentInventoryDto updateEquipment(Long id, EquipmentInventoryDto dto) {
        logger.info("Updating equipment id: {}", id);
        EquipmentInventory entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setQuantity(dto.getQuantity());
        entity.setAvailable(dto.getAvailable());
        entity.setCondition(dto.getCondition());
        entity.setLocation(dto.getLocation());
        EquipmentInventory saved = repository.save(entity);
        return mapper.map(saved, EquipmentInventoryDto.class);
    }

    @Override
    public void deleteEquipment(Long id) {
        logger.info("Deleting equipment id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public List<EquipmentInventoryDto> getAllEquipment() {
        logger.info("Fetching all equipment");
        return repository.findAll().stream()
                .map(e -> mapper.map(e, EquipmentInventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentInventoryDto getEquipmentById(Long id) {
        logger.info("Fetching equipment by id: {}", id);
        EquipmentInventory entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        return mapper.map(entity, EquipmentInventoryDto.class);
    }

    @Override
    public Page<EquipmentInventoryDto> findEquipmentByFilters(EquipmentInventoryCriteria criteria, Pageable pageable) {
        logger.info("Filtering equipment inventory");
        Specification<EquipmentInventory> spec = Specification
                .where(EquipmentInventorySpecification.matchName(criteria.getName()))
                .and(EquipmentInventorySpecification.matchCategory(criteria.getCategory()))
                .and(EquipmentInventorySpecification.matchCondition(criteria.getCondition()))
                .and(EquipmentInventorySpecification.matchLocation(criteria.getLocation()));
        Page<EquipmentInventory> page = repository.findAll(spec, pageable);
        return page.map(e -> mapper.map(e, EquipmentInventoryDto.class));
    }
}
>>>>>>> e4e22ab (Initial commit)
