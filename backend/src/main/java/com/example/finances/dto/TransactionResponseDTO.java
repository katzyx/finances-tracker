package com.example.finances.dto;

import java.time.LocalDate;

import com.example.finances.model.Transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}