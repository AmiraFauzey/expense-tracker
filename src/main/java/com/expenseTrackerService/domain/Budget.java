package com.expenseTrackerService.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.attribute.standard.MediaSize;
import java.math.BigDecimal;

@Entity
@Table(name = "budget")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BudgetID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID")
    private Category category;

    @Column(name = "LimitAmount")
    private BigDecimal limitAmount;

    @Column(name = "RemainingAmount")
    private BigDecimal remainingAmount;

    @Column(name = "TotalExpenses")
    private BigDecimal totalExpenses;
}
