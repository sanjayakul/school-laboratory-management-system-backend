package com.laboratory.management.system.controller;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.ChemicalInventoryDetailsDto;
import com.laboratory.management.system.service.ChemicalInventoryDetailsService;
import com.laboratory.management.system.service.criteria.ChemicalInventoryDetailsCriteria;
import com.laboratory.management.system.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "ChemicalInventoryDetailsController", description = "The Chemical Inventory Details API Services")
public class ChemicalInventoryDetailsController extends Response {

    private static final Logger logger = LoggerFactory.getLogger(ChemicalInventoryDetailsController.class);

    private final ChemicalInventoryDetailsService chemicalInventoryDetailsService;

    public ChemicalInventoryDetailsController(ChemicalInventoryDetailsService chemicalInventoryDetailsService) {
        this.chemicalInventoryDetailsService = chemicalInventoryDetailsService;
    }

    @PostMapping(value = "/chemical-inventory-details", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Chemical Inventory Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> createChemicalInventoryDetails(@RequestBody ChemicalInventoryDetailsDto chemicalInventoryDetailsDto) {
        logger.info("ChemicalInventoryDetailsController -> createLoanValidations");
        try {
            return ResponseEntity.ok(response(chemicalInventoryDetailsService.createChemicalInventoryDetails(chemicalInventoryDetailsDto), "chemical inventory created successfully"));
        } catch (DataIntegrityViolationException e) {
            logger.error("ChemicalInventoryDetailsController -> createChemicalInventoryDetails() => constraint violation exception : {}", e.getCause().getMessage());
            return ResponseEntity.badRequest().body(error(e.getMostSpecificCause().getMessage(), "constraint violation exception!"));
        } catch (Exception e) {
            logger.error("ChemicalInventoryDetailsController -> createChemicalInventoryDetails() => chemical inventory creation failed : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = "/chemical-inventory-details/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Filter chemical inventory details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public Page<ChemicalInventoryDetailsDto> findChemicalInventoryDetailsByFilters(@RequestParam(value = "status", required = false) String status,
                                                        @RequestParam(value = "chemicalName", required = false) String chemicalName,
                                                        @RequestParam(value = "chemicalFormula", required = false) String chemicalFormula,
                                                        @RequestParam(value = "casNumber", required = false) String casNumber,
                                                        @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size

    ) throws DataNotFoundException {
        var criteria = ChemicalInventoryDetailsCriteria
                .builder()
                .status(status)
                .chemicalName(chemicalName)
                .chemicalFormula(chemicalFormula)
                .casNumber(casNumber)
                .build();

        Pageable pageable = createPageRequestUsing(page, size);
        return chemicalInventoryDetailsService.findChemicalInventoryDetailsByFilters(criteria, pageable);
    }

    private Pageable createPageRequestUsing(int page, int size) {

        List<Sort.Order> orders = new ArrayList<>();

        orders.add(new Sort.Order(Sort.Direction.DESC, "modifiedDate"));

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
