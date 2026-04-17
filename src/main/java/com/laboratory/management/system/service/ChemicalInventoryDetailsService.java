<<<<<<< HEAD
package com.laboratory.management.system.service;

import com.laboratory.management.system.model.dto.ChemicalInventoryDetailsDto;
import com.laboratory.management.system.service.criteria.ChemicalInventoryDetailsCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChemicalInventoryDetailsService {

    ChemicalInventoryDetailsDto createChemicalInventoryDetails(ChemicalInventoryDetailsDto dto);
    Page<ChemicalInventoryDetailsDto> findChemicalInventoryDetailsByFilters(ChemicalInventoryDetailsCriteria criteria, Pageable pageable);
}
=======
package com.laboratory.management.system.service;

import com.laboratory.management.system.model.dto.ChemicalInventoryDetailsDto;
import com.laboratory.management.system.service.criteria.ChemicalInventoryDetailsCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChemicalInventoryDetailsService {

    ChemicalInventoryDetailsDto createChemicalInventoryDetails(ChemicalInventoryDetailsDto dto);
    Page<ChemicalInventoryDetailsDto> findChemicalInventoryDetailsByFilters(ChemicalInventoryDetailsCriteria criteria, Pageable pageable);
}
>>>>>>> e4e22ab (Initial commit)
