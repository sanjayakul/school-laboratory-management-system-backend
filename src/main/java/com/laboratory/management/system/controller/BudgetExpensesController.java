package com.laboratory.management.system.controller;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.BudgetCategoryDto;
import com.laboratory.management.system.model.dto.BudgetExpenseDto;
import com.laboratory.management.system.service.BudgetExpenseService;
import com.laboratory.management.system.util.Response;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "BudgetExpensesController", description = "The Budget Expenses API Services")
public class BudgetExpensesController extends Response {

    private static final Logger logger = LoggerFactory.getLogger(BudgetExpensesController.class);

    @Autowired
    private BudgetExpenseService budgetExpenseService;

    @PostMapping(value = "/budget-expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Budget Expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> createBudgetExpenses(@RequestBody BudgetExpenseDto budgetExpenseDto) {
        {
            logger.info("BudgetExpensesController -> createBudgetExpenses");
            try {
                return ResponseEntity.ok(response(budgetExpenseService.createBudgetExpenses(budgetExpenseDto), "budget expense created successfully"));
            } catch (DataIntegrityViolationException e) {
                logger.error("BudgetExpensesController -> createBudgetExpenses() => constraint violation exception : {}", e.getCause().getMessage());
                return ResponseEntity.badRequest().body(error(e.getMostSpecificCause().getMessage(), "constraint violation exception!"));
            } catch (Exception e) {
                logger.error("BudgetExpensesController -> createBudgetExpenses() => budget expense creation failed : {}", e.getMessage());
                return ResponseEntity.internalServerError().body(error(e.getMessage()));
            }
        }
    }

    @GetMapping(value = "/budget-expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get All Budget Expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BudgetCategoryDto.class))))})
    public List<BudgetExpenseDto> getAll() {
        logger.info("Start getting all the Budget Expenses");
        return budgetExpenseService.getAllBudgetExpenses();
    }

    @PutMapping(value = "/budget-expenses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Budget Expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> updateBudgetExpenses(@PathVariable Long id, @RequestBody BudgetExpenseDto budgetExpenseDto) {
        logger.info("BudgetExpensesController -> updateBudgetExpenses -> started ");
        try {
            return ResponseEntity.ok(response(budgetExpenseService.updateBudgetExpenses(id, budgetExpenseDto), "Budget expense updated successfully"));
        } catch (DataNotFoundException e) {
            logger.error("BudgetExpensesController -> updateBudgetExpenses() => data not found exception : {}", e.getMessage());
            return ResponseEntity.badRequest().body(error(e.getMessage(), "data not found exception!"));
        } catch (DataIntegrityViolationException e) {
            logger.error("BudgetExpensesController -> updateBudgetExpenses() => constraint violation exception : {}", e.getCause().getMessage());
            return ResponseEntity.badRequest().body(error(e.getMostSpecificCause().getMessage(), "constraint violation exception!"));
        } catch (Exception e) {
            logger.error("BudgetExpensesController -> updateBudgetExpenses() => Budget expense update failed : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }

    }
}
