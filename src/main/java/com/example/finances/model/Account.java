package com.example.finances.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User userId;

    @Column(nullable = false, name = "account_name")
    private String accountName;

    @Column(nullable = false, name = "account_balance")
    private BigDecimal accountBalance;

    public Account() {
    }

    public Account(User userId, String accountName, BigDecimal accountBalance) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
    }
}



