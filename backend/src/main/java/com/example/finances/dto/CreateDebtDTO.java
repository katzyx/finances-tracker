package com.example.finances.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Data Transfer Object for creating a new Debt.
 * This class is used to receive data from the API request body,
 * separating it from the JPA entity model.
 */
public class CreateDebtDTO {

    @NotNull(message = "User ID is mandatory")
    @Min(value = 1, message = "User ID must be a positive integer")
    private Integer userId;

    @NotBlank(message = "Debt name is mandatory")
    private String debtName;

    @NotNull(message = "Total owed amount is mandatory")
    @DecimalMin(value = "0.01", message = "Total owed must be a positive value")
    private BigDecimal totalOwed;

    @DecimalMin(value = "0.00", message = "Amount paid cannot be negative")
    private BigDecimal amountPaid = BigDecimal.ZERO; // Defaults to 0

    @NotNull(message = "Monthly payment is mandatory")
    @DecimalMin(value = "0.01", message = "Monthly payment must be a positive value")
    private BigDecimal monthlyPayment;

    public CreateDebtDTO() {}

    public CreateDebtDTO(Integer userId, String debtName, BigDecimal totalOwed, 
                        BigDecimal amountPaid, BigDecimal monthlyPayment) {
        this.userId = userId;
        this.debtName = debtName;
        this.totalOwed = totalOwed;
        this.amountPaid = amountPaid;
        this.monthlyPayment = monthlyPayment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public BigDecimal getTotalOwed() {
        return totalOwed;
    }

    public void setTotalOwed(BigDecimal totalOwed) {
        this.totalOwed = totalOwed;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
}