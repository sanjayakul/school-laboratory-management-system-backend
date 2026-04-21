package com.laboratory.management.system.controller;

import com.laboratory.management.system.model.dto.BudgetCategoryDto;
import com.laboratory.management.system.service.BudgetCategoryService;
import com.laboratory.management.system.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "BudgetCategoryController", description = "The Budget Category API Services")
public class BudgetCategoryController extends Response {

    private static final Logger logger = LoggerFactory.getLogger(BudgetCategoryController.class);

    @Autowired
    private BudgetCategoryService budgetCategoryService;

    @PostMapping(value = "/budget-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Budget Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> createBudgetCategory(@RequestBody BudgetCategoryDto budgetCategoryDto) {
        logger.info("BudgetCategoryController -> createBudgetCategory");
        try {
            return ResponseEntity.ok(response(budgetCategoryService.createBudgetCategory(budgetCategoryDto), "budget category created successfully"));
        } catch (DataIntegrityViolationException e) {
            logger.error("BudgetCategoryController -> createBudgetCategory() => constraint violation exception : {}", e.getCause().getMessage());
            return ResponseEntity.badRequest().body(error(e.getMostSpecificCause().getMessage(), "constraint violation exception!"));
        } catch (Exception e) {
            logger.error("BudgetCategoryController -> createBudgetCategory() => budget category creation failed : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = "/budget-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get All Budget Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BudgetCategoryDto.class))))})
    public List<BudgetCategoryDto> getAll() {
        logger.info("Start getting all the Budget Categories");
        return budgetCategoryService.getAllBudgetCategories();
    }

/*    @GetMapping(value = "/budget-categories/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Budget Category Summaries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> getBudgetCategorySummaries() {
        logger.info("BudgetCategoryController -> getBudgetCategorySummaries");
        try {
            return ResponseEntity.ok(response(budgetCategoryService.getBudgetCategorySummaries(), "budget category summaries fetched successfully"));
        } catch (Exception e) {
            logger.error("BudgetCategoryController -> getBudgetCategorySummaries() => failed : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }*/

}
