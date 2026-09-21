package com.ekabotdev.expense_tracker_api.repository;

import com.ekabotdev.expense_tracker_api.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
