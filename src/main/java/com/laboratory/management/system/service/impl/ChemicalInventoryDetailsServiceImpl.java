<<<<<<< HEAD
package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.model.ChemicalInventoryDetails;
import com.laboratory.management.system.model.dto.ChemicalInventoryDetailsDto;
import com.laboratory.management.system.repository.ChemicalInventoryDetailsRepository;
import com.laboratory.management.system.service.ChemicalInventoryDetailsService;
import com.laboratory.management.system.service.criteria.ChemicalInventoryDetailsCriteria;
import com.laboratory.management.system.service.specification.ChemicalInventoryDetailsSpecification;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.laboratory.management.system.util.Enums.ACTIVE;

@Service
public class ChemicalInventoryDetailsServiceImpl implements ChemicalInventoryDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ChemicalInventoryDetailsServiceImpl.class);

    private final ChemicalInventoryDetailsRepository chemicalInventoryDetailsRepository;
    private final ModelMapper mapper;

    public ChemicalInventoryDetailsServiceImpl(ChemicalInventoryDetailsRepository chemicalInventoryDetailsRepository, ModelMapper mapper) {
        this.chemicalInventoryDetailsRepository = chemicalInventoryDetailsRepository;
        this.mapper = mapper;
    }

    @Override
    public ChemicalInventoryDetailsDto createChemicalInventoryDetails(ChemicalInventoryDetailsDto dto) {

        logger.info("ChemicalInventoryDetailsServiceImpl => createChemicalInventoryDetails() => started!");

        try {

            ChemicalInventoryDetails entity = mapper.map(dto, ChemicalInventoryDetails.class);

            entity.setChemicalName(dto.getChemicalName());
            entity.setChemicalFormula(dto.getChemicalFormula());
            entity.setQuantity(dto.getQuantity());
            entity.setUnit(dto.getUnit());
            entity.setMinStockLevel(dto.getMinStockLevel());
            entity.setHazardLevel(dto.getHazardLevel());
            entity.setStorageLocation(dto.getStorageLocation());
            entity.setExpirationDate(dto.getExpirationDate());
            entity.setCasNumber(dto.getCasNumber());
            entity.setSupplier(dto.getSupplier());
            entity.setSafetyNotes(dto.getSafetyNotes());

            entity.setStatus(ACTIVE.value());

            ChemicalInventoryDetails savedEntity = chemicalInventoryDetailsRepository.save(entity);

            logger.info("ChemicalInventoryDetailsServiceImpl => createChemicalInventoryDetails() => Finished!");

            return mapper.map(savedEntity, ChemicalInventoryDetailsDto.class);

        } catch (Exception ex) {
            logger.error("Error in create Chemical InventoryDetails: ", ex);
            throw new RuntimeException("Failed to create Chemical Inventory Details", ex);
        }
    }

    @Override
    public Page<ChemicalInventoryDetailsDto> findChemicalInventoryDetailsByFilters(ChemicalInventoryDetailsCriteria criteria, Pageable pageable) {
        logger.info("ChemicalInventoryDetailsServiceImpl -> findChemicalInventoryDetailsByFilters => Started!");

        Specification specification = Specification
                .where(ChemicalInventoryDetailsSpecification.matchStatus(criteria.getStatus()))
                .and(ChemicalInventoryDetailsSpecification.matchChemicalName(criteria.getChemicalName()))
                .and(ChemicalInventoryDetailsSpecification.matchChemicalFormula(criteria.getChemicalFormula()))
                .and(ChemicalInventoryDetailsSpecification.matchCasNumber(criteria.getCasNumber()));

        Page<ChemicalInventoryDetails> chemicalInventoryDetails = chemicalInventoryDetailsRepository.findAll(specification, pageable);
        Page<ChemicalInventoryDetailsDto> chemicalInventoryDetailsDtos = chemicalInventoryDetails.map(summary -> mapper.map(summary, ChemicalInventoryDetailsDto.class));

        return chemicalInventoryDetailsDtos;
    }
}
=======
package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.model.ChemicalInventoryDetails;
import com.laboratory.management.system.model.dto.ChemicalInventoryDetailsDto;
import com.laboratory.management.system.repository.ChemicalInventoryDetailsRepository;
import com.laboratory.management.system.service.ChemicalInventoryDetailsService;
import com.laboratory.management.system.service.criteria.ChemicalInventoryDetailsCriteria;
import com.laboratory.management.system.service.specification.ChemicalInventoryDetailsSpecification;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.laboratory.management.system.util.Enums.ACTIVE;

@Service
public class ChemicalInventoryDetailsServiceImpl implements ChemicalInventoryDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ChemicalInventoryDetailsServiceImpl.class);

    private final ChemicalInventoryDetailsRepository chemicalInventoryDetailsRepository;
    private final ModelMapper mapper;

    public ChemicalInventoryDetailsServiceImpl(ChemicalInventoryDetailsRepository chemicalInventoryDetailsRepository, ModelMapper mapper) {
        this.chemicalInventoryDetailsRepository = chemicalInventoryDetailsRepository;
        this.mapper = mapper;
    }

    @Override
    public ChemicalInventoryDetailsDto createChemicalInventoryDetails(ChemicalInventoryDetailsDto dto) {

        logger.info("ChemicalInventoryDetailsServiceImpl => createChemicalInventoryDetails() => started!");

        try {

            ChemicalInventoryDetails entity = mapper.map(dto, ChemicalInventoryDetails.class);

            entity.setChemicalName(dto.getChemicalName());
            entity.setChemicalFormula(dto.getChemicalFormula());
            entity.setQuantity(dto.getQuantity());
            entity.setUnit(dto.getUnit());
            entity.setMinStockLevel(dto.getMinStockLevel());
            entity.setHazardLevel(dto.getHazardLevel());
            entity.setStorageLocation(dto.getStorageLocation());
            entity.setExpirationDate(dto.getExpirationDate());
            entity.setCasNumber(dto.getCasNumber());
            entity.setSupplier(dto.getSupplier());
            entity.setSafetyNotes(dto.getSafetyNotes());

            entity.setStatus(ACTIVE.value());

            ChemicalInventoryDetails savedEntity = chemicalInventoryDetailsRepository.save(entity);

            logger.info("ChemicalInventoryDetailsServiceImpl => createChemicalInventoryDetails() => Finished!");

            return mapper.map(savedEntity, ChemicalInventoryDetailsDto.class);

        } catch (Exception ex) {
            logger.error("Error in create Chemical InventoryDetails: ", ex);
            throw new RuntimeException("Failed to create Chemical Inventory Details", ex);
        }
    }

    @Override
    public Page<ChemicalInventoryDetailsDto> findChemicalInventoryDetailsByFilters(ChemicalInventoryDetailsCriteria criteria, Pageable pageable) {
        logger.info("ChemicalInventoryDetailsServiceImpl -> findChemicalInventoryDetailsByFilters => Started!");

        Specification specification = Specification
                .where(ChemicalInventoryDetailsSpecification.matchStatus(criteria.getStatus()))
                .and(ChemicalInventoryDetailsSpecification.matchChemicalName(criteria.getChemicalName()))
                .and(ChemicalInventoryDetailsSpecification.matchChemicalFormula(criteria.getChemicalFormula()))
                .and(ChemicalInventoryDetailsSpecification.matchCasNumber(criteria.getCasNumber()));

        Page<ChemicalInventoryDetails> chemicalInventoryDetails = chemicalInventoryDetailsRepository.findAll(specification, pageable);
        Page<ChemicalInventoryDetailsDto> chemicalInventoryDetailsDtos = chemicalInventoryDetails.map(summary -> mapper.map(summary, ChemicalInventoryDetailsDto.class));

        return chemicalInventoryDetailsDtos;
    }
}
>>>>>>> e4e22ab (Initial commit)
