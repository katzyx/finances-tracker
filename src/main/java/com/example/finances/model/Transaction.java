package com.example.finances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "transaction_id")
    private int transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable= false, name = "account_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Account accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User userId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debt_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Debt debtId;

    @Column(nullable = false, name = "transaction_date")
    private LocalDate transactionDate;

    @Column(nullable = false, name = "type")
    private String type; // "income" or "expense"

    @Column(name = "recurrence")
    private String recurrence; // "weekly", "monthly", "yearly", or null

    public Transaction() {
    }

    public Transaction(Account accountId, User userId, double amount, String description,
                       Category categoryId, Debt debtId, LocalDate transactionDate,
                       String type, String recurrence) {
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
        this.debtId = debtId;
        this.transactionDate = transactionDate;
        this.type = type;
        this.recurrence = recurrence;
    }
}