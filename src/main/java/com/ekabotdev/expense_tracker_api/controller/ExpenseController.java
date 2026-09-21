package com.ekabotdev.expense_tracker_api.controller;

import com.ekabotdev.expense_tracker_api.dto.CreateExpenseRequest;
import com.ekabotdev.expense_tracker_api.dto.ExpenseResponse;
import com.ekabotdev.expense_tracker_api.dto.UpdateExpenseRequest;
import com.ekabotdev.expense_tracker_api.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses",
description = "Expense management endpoints")
public class ExpenseController {


    private final ExpenseService expenseService;

    @Operation(
            summary = "Create an expense",
            description = "Creates an expense"
    )
    @PostMapping
    public ResponseEntity<ExpenseResponse>
    createExpense(  @Valid  @RequestBody CreateExpenseRequest request) {

        ExpenseResponse expenseResponse = expenseService.createExpense(request);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expenseResponse);
    }


    @Operation(
            summary = "Get all expenses",
            description = "Reads all expenses"
    )
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        List<ExpenseResponse> expenseResponseList = expenseService.getAllExpenses();
        return ResponseEntity
                .ok(expenseResponseList);
    }


    @Operation(
            summary = "Get an expense by ID",
            description = "Reads an expense by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        ExpenseResponse expenseResponse = expenseService.getExpenseById(id);
        return ResponseEntity
                .ok(expenseResponse);
    }

    @Operation(
            summary = "Update an expense",
            description = "Updates an expense"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody UpdateExpenseRequest request) {

        ExpenseResponse response =
                expenseService.updateExpense(id, request);

        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Delete an expense",
            description = "Deletes an expense"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }
}
