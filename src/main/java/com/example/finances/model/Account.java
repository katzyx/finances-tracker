package com.example.finances.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;

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

    public int getAccountId() {
        return this.accountId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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



