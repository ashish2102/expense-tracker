package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Marks this as a REST API controller
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired  // Injects the ExpenseRepository for database operations
    private ExpenseRepository expenseRepository;

    // ✅ 1️⃣ Get All Expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();  // Fetches all expenses from DB
    }

    // ✅ 2️⃣ Get Expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 3️⃣ Create a New Expense
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);  // Saves expense to DB
    }

    // ✅ 4️⃣ Update an Expense
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense newExpense) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setName(newExpense.getName());
                    expense.setAmount(newExpense.getAmount());
                    expense.setCategory(newExpense.getCategory());
                    expense.setDate(newExpense.getDate());
                    Expense updatedExpense = expenseRepository.save(expense);
                    return ResponseEntity.ok(updatedExpense);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 5️⃣ Delete an Expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
