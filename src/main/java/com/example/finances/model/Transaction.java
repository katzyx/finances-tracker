package com.example.finances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "transaction_id")
    private int transactionId;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable= false, name = "account_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Account accountId;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User userId;

    @Getter
    @Setter
    @Column(nullable = false)
    private double amount;

    @Getter
    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category categoryId;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debt_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Debt debtId;

    @Setter
    @Getter
    @Column(nullable = false, name = "transaction_date")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LocalDate transactionDate;

    @Setter
    @Getter
    @Column(nullable = false, name = "type")
    private String type;

    @Getter
    @Setter
    @Column(nullable = false, name = "recurrence")
    private String recurrence;

    public Transaction() {
    }

    public Transaction(Account accountId, User userId, double amount, String description, Category categoryId, Debt debtId, LocalDate transactionDate, String type, String recurrence) {
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
