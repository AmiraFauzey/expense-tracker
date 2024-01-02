package com.expenseTrackerService.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UserID")
    private Long id;

    @Column(name="Username")
    private String userName;

    @Column(name="Password")
    private String password;

    @Column(name="Email")
    private String email;

    @Column(name="Salary")
    private BigDecimal salary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Expense> expenses;

}
