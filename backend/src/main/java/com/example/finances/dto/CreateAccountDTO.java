package com.example.finances.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountDTO {
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotBlank(message = "Account name cannot be blank")
    private String accountName;

    @NotNull(message = "Account balance cannot be null")
    @PositiveOrZero(message = "Account balance must be a positive number or zero")
    private BigDecimal accountBalance;

}
