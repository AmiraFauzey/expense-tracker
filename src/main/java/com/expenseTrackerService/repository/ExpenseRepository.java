package com.expenseTrackerService.repository;

import com.expenseTrackerService.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long>, JpaSpecificationExecutor<Expense> {

    Expense findByid (Long id);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.category.id = :categoryId")
    BigDecimal getTotalExpensesForCategory(@Param("categoryId") Long categoryId);

    Page<Expense> findAll(Specification<Expense> specification, Pageable pageable);
}
