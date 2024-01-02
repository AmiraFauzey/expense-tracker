package com.expenseTrackerService.specification;

import com.expenseTrackerService.domain.Expense;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ExpenseSpecification {

    public static Specification<Expense> nameLike(String expenseNameLike) {
        return (root, query, builder) -> builder.like(root.get("expenseName").get("name"), "%" + expenseNameLike + "%");
    }

    public static Specification<Expense> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("category").get("categoryName"),"%" +  categoryName + "%");
    }

    public static Specification<Expense> hasAmountLessThanEqual(Double amount) {
        return (root, query, criteriaBuilder) ->
           criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amount);
    }
}
