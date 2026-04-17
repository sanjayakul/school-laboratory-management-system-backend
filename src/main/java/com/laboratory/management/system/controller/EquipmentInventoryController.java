package com.laboratory.management.system.controller;

import com.laboratory.management.system.model.dto.EquipmentInventoryDto;
import com.laboratory.management.system.service.EquipmentInventoryService;
import com.laboratory.management.system.service.criteria.EquipmentInventoryCriteria;
import com.laboratory.management.system.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "EquipmentInventoryController", description = "The Equipment Inventory API Services")
public class EquipmentInventoryController extends Response {

    private final EquipmentInventoryService equipmentInventoryService;

    public EquipmentInventoryController(EquipmentInventoryService equipmentInventoryService) {
        this.equipmentInventoryService = equipmentInventoryService;
    }

    @PostMapping(value = "/equipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createEquipment(@RequestBody EquipmentInventoryDto dto) {
        return ResponseEntity.ok(response(equipmentInventoryService.createEquipment(dto), "Equipment created successfully"));
    }

    @PutMapping(value = "/equipments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateEquipment(@PathVariable Long id, @RequestBody EquipmentInventoryDto dto) {
        return ResponseEntity.ok(response(equipmentInventoryService.updateEquipment(id, dto), "Equipment updated successfully"));
    }

    @DeleteMapping(value = "/equipments/{id}")
    public ResponseEntity<Response> deleteEquipment(@PathVariable Long id) {
        equipmentInventoryService.deleteEquipment(id);
        return ResponseEntity.ok(response(null, "Equipment deleted successfully"));
    }

    @GetMapping(value = "/equipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getAllEquipment() {
        List<EquipmentInventoryDto> list = equipmentInventoryService.getAllEquipment();
        return ResponseEntity.ok(response(list, "Equipment list fetched successfully"));
    }

    @GetMapping(value = "/equipments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getEquipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(response(equipmentInventoryService.getEquipmentById(id), "Equipment fetched successfully"));
    }

    @GetMapping(value = "/equipments/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Filter equipment inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public Page<EquipmentInventoryDto> findEquipmentByFilters(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        var criteria = EquipmentInventoryCriteria.builder()
                .name(name)
                .category(category)
                .condition(condition)
                .location(location)
                .build();
        Pageable pageable = createPageRequestUsing(page, size);
        return equipmentInventoryService.findEquipmentByFilters(criteria, pageable);
    }

    private Pageable createPageRequestUsing(int page, int size) {
        List<Sort.Order> orders = new java.util.ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        return PageRequest.of(page, size, Sort.by(orders));
    }
}
