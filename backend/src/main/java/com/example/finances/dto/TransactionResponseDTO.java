package com.example.finances.dto;

import java.time.LocalDate;
import com.example.finances.model.Transaction;

public class TransactionResponseDTO {
    private int transactionId;
    private int accountId;
    private String accountName;
    private int userId;
    private int categoryId;
    private String categoryName;
    private Integer debtId;
    private String debtName;
    private double amount;
    private String description;
    private String type;
    private LocalDate transactionDate;
    private String recurrence;

    public TransactionResponseDTO() {}

    public TransactionResponseDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.accountId = transaction.getAccountId().getAccountId();
        this.accountName = transaction.getAccountId().getAccountName();
        this.userId = transaction.getUserId().getUserId();
        this.categoryId = transaction.getCategoryId().getCategoryId();
        this.categoryName = transaction.getCategoryId().getCategoryName();
        this.debtId = (transaction.getDebtId() != null) ? transaction.getDebtId().getDebtId() : null;
        this.debtName = (transaction.getDebtId() != null) ? transaction.getDebtId().getDebtName() : null;
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.transactionDate = transaction.getTransactionDate();
        this.recurrence = transaction.getRecurrence();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }
}