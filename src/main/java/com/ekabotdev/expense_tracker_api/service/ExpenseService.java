package com.ekabotdev.expense_tracker_api.service;

import com.ekabotdev.expense_tracker_api.dto.CreateExpenseRequest;
import com.ekabotdev.expense_tracker_api.dto.ExpenseResponse;
import com.ekabotdev.expense_tracker_api.dto.UpdateExpenseRequest;
import com.ekabotdev.expense_tracker_api.entity.Expense;
import com.ekabotdev.expense_tracker_api.exception.ExpenseNotFoundException;
import com.ekabotdev.expense_tracker_api.repository.ExpenseRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final EntityManager manager;


    @Transactional
    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());

        Expense savedExpense = expenseRepository.saveAndFlush(expense);
        manager.refresh(savedExpense);

        return toResponse(savedExpense);


    }


    private ExpenseResponse toResponse(Expense expense) {

        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getAllExpenses(){
        List<ExpenseResponse> expenses = new ArrayList<>();
        List<Expense> expensesList = expenseRepository.findAll();
        for (Expense expense : expensesList) {
            expenses.add(toResponse(expense));
        }
        return expenses;
    }

    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseById(Long id){
        Expense expense = expenseRepository.findById(id).orElseThrow(()
                -> new ExpenseNotFoundException("Expense with id " + id + " not found"));

        return toResponse(expense);
    }

    @Transactional
    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request) {

        Expense expense = expenseRepository.findById(id).orElseThrow(()
                -> new ExpenseNotFoundException("Expense with id " + id + " not found"));

        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());

        Expense updatedExpense = expenseRepository.saveAndFlush(expense);
        manager.refresh(updatedExpense);
        return toResponse(updatedExpense);

    }

    @Transactional
    public void deleteExpense(Long id){
        Expense expense = expenseRepository.findById(id).orElseThrow(()
                -> new ExpenseNotFoundException("Expense with id " + id + " not found"));
        expenseRepository.delete(expense);
    }
}
