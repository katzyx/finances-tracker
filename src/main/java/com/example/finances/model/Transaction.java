package com.example.finances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "transactions")
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LocalDate transactionDate;

    public Transaction() {

    }

    public Transaction(Account accountId, User userId, double amount, String description, Category categoryId, Debt debtId, LocalDate transactionDate) {
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
        this.debtId = debtId;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Debt getDebtId() {
        return debtId;
    }

    public void setDebtId(Debt debtId) {
        this.debtId = debtId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
