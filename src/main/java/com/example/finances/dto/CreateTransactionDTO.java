package com.example.finances.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object for creating a new Transaction.
 * This class is used to receive data from the API request body,
 * separating it from the JPA entity model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}