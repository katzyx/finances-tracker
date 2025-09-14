package com.example.finances.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateAccountRequest {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotBlank(message = "Account name cannot be blank")
    private String accountName;

    @NotNull(message = "Account balance cannot be null")
    @PositiveOrZero(message = "Account balance must be a positive number or zero")
    private BigDecimal accountBalance;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}
