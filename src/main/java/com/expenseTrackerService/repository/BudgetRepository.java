package com.expenseTrackerService.repository;

import com.expenseTrackerService.domain.Budget;
import com.expenseTrackerService.domain.Expense;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long>{

    Optional<Budget> findByUserIdAndCategoryId(Long userId, Long categoryId);

    Budget findByid(Long id);
}
