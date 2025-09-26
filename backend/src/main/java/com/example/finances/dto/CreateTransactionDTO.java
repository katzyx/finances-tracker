package com.example.finances.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new Transaction.
 * This class is used to receive data from the API request body,
 * separating it from the JPA entity model.
 */
public class CreateTransactionDTO {

    @NotNull(message = "Account ID is mandatory")
    @Min(value = 1, message = "Account ID must be a positive integer")
    private Integer accountId;

    @NotNull(message = "User ID is mandatory")
    @Min(value = 1, message = "User ID must be a positive integer")
    private Integer userId;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be a positive value")
    private Double amount;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Category ID is mandatory")
    @Min(value = 1, message = "Category ID must be a positive integer")
    private Integer categoryId;

    private Integer debtId; // Optional

    @NotBlank(message = "Type is mandatory")
    @Pattern(regexp = "^(income|expense)$", message = "Type must be either 'income' or 'expense'")
    private String type;

    @Pattern(regexp = "^(weekly|monthly|yearly)?$", message = "Recurrence must be 'weekly', 'monthly', 'yearly', or empty")
    private String recurrence; // Optional - can be null or empty string

    public CreateTransactionDTO() {}

    public CreateTransactionDTO(Integer accountId, Integer userId, Double amount, 
                               String description, Integer categoryId, Integer debtId, 
                               String type, String recurrence) {
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
        this.debtId = debtId;
        this.type = type;
        this.recurrence = recurrence;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }
}