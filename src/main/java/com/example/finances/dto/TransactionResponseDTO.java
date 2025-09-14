package com.example.finances.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private int transactionId;
    private int accountId;
    private int userId;
    private double amount;
    private String description;
    private int categoryId;
    private Integer debtId;
    private LocalDate transactionDate;
}
