package com.expenseTrackerService.service;

import com.expenseTrackerService.domain.*;
import com.expenseTrackerService.exception.BusinessException;
import com.expenseTrackerService.exception.ResourceNotFoundException;
import com.expenseTrackerService.repository.BudgetRepository;
import com.expenseTrackerService.repository.ExpenseRepository;
import com.expenseTrackerService.specification.ExpenseSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    /*
    * 1.  Implement methods for creating, updating, and deleting expenses, as well as retrieving expense information.
    * */

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            BudgetRepository budgetRepository
    ){
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
    }

    //1. Get All expense
    public List<Expense> getAllExpense(){
        return expenseRepository.findAll();
    }

    //2. Get expense by id
    public Expense findByExpenseId(Long id){
        Expense expenseId = expenseRepository.findByid(id);
        if(expenseId == null){
            throw new ResourceNotFoundException("Not found expense with Id =" + expenseId);
        }
        return  expenseRepository.findByid(id);
    }

    //3. Create expense
    @Transactional
    public Expense createExpense(Expense expense){

        // 1. Subtract the amount of the new expense from the budget of the corresponding category
        Budget budget = budgetRepository.findByUserIdAndCategoryId(expense.getUser().getId(), expense.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found for user and category"));

        BigDecimal remainingBudgetAmount = budget.getLimitAmount().subtract(expense.getAmount());
        budget.setRemainingAmount(remainingBudgetAmount);

        // 2. Calculate total expenses for the category
        BigDecimal totalExpensesForCategory = expenseRepository.getTotalExpensesForCategory(expense.getCategory().getId());
        if (totalExpensesForCategory == null) {
            totalExpensesForCategory = BigDecimal.ZERO;
        }

        // Ensure that the new expense does not exceed the category limit
        BigDecimal newTotalExpenses = totalExpensesForCategory.add(expense.getAmount());
        if (newTotalExpenses.compareTo(budget.getLimitAmount()) > 0) {
            // Handle the case where the new expense exceeds the category limit
            throw new BusinessException("Expense amount exceeds the category limit");
        }

        // Update the total expenses for the category
        budget.setTotalExpenses(newTotalExpenses);
        budgetRepository.save(budget);
        return expenseRepository.save(expense);
    }

    //4. update expense
    @Transactional
    public Expense updateExpense(Long expenseId, Expense expense){

        //check if expense existed
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));

        // Update existing expense fields with the new values
        existingExpense.setExpenseName(expense.getExpenseName());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());

        return expenseRepository.save(existingExpense);
    }

    //5. Delete expense
    @Transactional
    public void deleteExpense(Long expenseId) {
        // Additional validation and business logic can be added here
        Expense existingExpense = expenseRepository.findByid(expenseId);
        if (existingExpense != null){
            expenseRepository.delete(existingExpense);
        }else{
            throw new ResourceNotFoundException("Not found expense with Id =" + expenseId);
        }
    }

    //6. Searching and pageable
    //6. Search, sort, pagination bookmark
    public Page<Expense> searchExpenses(String expenseName, String category, Double amount, Pageable pageable) {
        Specification<Expense> spec = Specification.where(null);

        if (expenseName != null && !expenseName.isEmpty()) {
            spec = spec.and(ExpenseSpecification.nameLike(expenseName));
        }

        if (category != null && !category.isEmpty()) {
            spec = spec.and(ExpenseSpecification.hasCategoryName(category));
        }

        if (amount != null) {
            spec = spec.and(ExpenseSpecification.hasAmountLessThanEqual(amount));
        }

        return expenseRepository.findAll(spec, pageable);
    }
}
