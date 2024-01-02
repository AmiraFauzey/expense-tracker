package com.expenseTrackerService.controller;

import com.expenseTrackerService.domain.Expense;
import com.expenseTrackerService.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    //1. Create New Bookmark
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense)
    {
        return expenseService.createExpense(expense);
    }

    //2. Get Bookmark By Id
    @GetMapping("{id}")
    public Expense getExpenseId(@PathVariable Long expenseId) {
        return expenseService.findByExpenseId(expenseId);
    }

    //3. Update the Bookmark
    @PutMapping("{id}")
    public Expense updateExpense(
            @PathVariable Long expenseId,
            @RequestBody  Expense expense) throws Exception {

        return expenseService.updateExpense(expenseId, expense);

    }

    //4. Delete the Bookmark
    @DeleteMapping("{id}")
    public void deleteExpense(@PathVariable Long expenseId) {
        Expense existingExpense = expenseService.findByExpenseId(expenseId);

        if (existingExpense != null) {
            expenseService.deleteExpense(expenseId);
        }
    }

    //5. Get All Bookmarks
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpense();
    }

    //6. Filter, Sorting, Pagination bookmarks
    @GetMapping("/expenses")
    public Page<Expense> searchExpenses(
            @RequestParam(required = false) String expenseName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double amount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return expenseService.searchExpenses(expenseName, category,amount, pageable);
    }
}
