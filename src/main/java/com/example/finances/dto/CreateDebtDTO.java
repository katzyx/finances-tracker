package com.example.finances.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for creating a new Debt.
 * This class is used to receive data from the API request body,
 * separating it from the JPA entity model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}