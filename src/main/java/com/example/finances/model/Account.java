package com.example.finances.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User userId;

    @Column(nullable = false, name = "account_name")
    private String accountName;

    @Column(nullable = false, name = "account_balance")
    private int accountBalance;

    public Account() {
    }

    public Account(User userId, String accountName, int accountBalance) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
    }

    public int getAccount() {
        return this.accountId;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}



