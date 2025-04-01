package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // ✅ Get all expenses for a user (with optional sorting in controller)
    List<Expense> findByUser(User user);

    // ✅ Filter by category for a specific user
    List<Expense> findByUserAndCategory(User user, String category);

    // ✅ Filter by date range for a specific user
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    // ✅ Filter by amount range for a specific user
    List<Expense> findByUserAndAmountBetween(User user, BigDecimal minAmount, BigDecimal maxAmount);

    // ✅ Filter by category and date range for a specific user
    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.category = :category AND e.date BETWEEN :startDate AND :endDate")
    List<Expense> findByUserCategoryAndDateRange(
            @Param("user") User user,
            @Param("category") String category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    @Query("SELECT e FROM Expense e WHERE e.user = :user " +
        "AND (:category IS NULL OR e.category = :category) " +
        "AND (:startDate IS NULL OR e.date >= :startDate) " +
        "AND (:endDate IS NULL OR e.date <= :endDate) " +
        "AND (:minAmount IS NULL OR e.amount >= :minAmount) " +
        "AND (:maxAmount IS NULL OR e.amount <= :maxAmount)")
    List<Expense> findFilteredExpenses(
        @Param("user") User user,
        @Param("category") String category,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount
);

}
