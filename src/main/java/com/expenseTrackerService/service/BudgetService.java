package com.expenseTrackerService.service;

import com.expenseTrackerService.domain.Budget;
import com.expenseTrackerService.exception.ResourceNotFoundException;
import com.expenseTrackerService.repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(
            BudgetRepository budgetRepository
    ){
        this.budgetRepository = budgetRepository;
    }

    //1. create new budget
    @Transactional
    public Budget createExpense(Budget expense){
        return budgetRepository.save(expense);
    }


    //2. update budget
    @Transactional
    public Budget updateBudget(Long budgetId, Budget budget){
        //check if expense existed
        Budget existingBudget = budgetRepository.findByid(budgetId);

        if(existingBudget == null){
            throw new ResourceNotFoundException("Not found budget with Id =" + budgetId);
        }
        existingBudget.setCategory(budget.getCategory());
        existingBudget.setLimitAmount(budget.getLimitAmount());
        return budgetRepository.save(existingBudget);
    }

    //3. get all budget
    public List<Budget> getAllBudget(){
        return budgetRepository.findAll();
    }


    //2. get budget by Id
    public Budget findByExpenseId(Long id){
        Budget budgetId = budgetRepository.findByid(id);
        if(budgetId == null){
            throw new ResourceNotFoundException("Not found expense with Id =" + budgetId);
        }
        return  budgetRepository.findByid(id);
    }


    //pagination and searching
}
