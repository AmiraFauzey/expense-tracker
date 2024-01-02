package com.expenseTrackerService.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expensename")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID")
    private Category category;

    @Column(name = "ExpenseName")
    private String name;

}
