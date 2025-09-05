package com.example.finances.model;

import jakarta.persistence.*;

@Entity
@Table(name = "debt")
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_id")
    private int debtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User userId;

    @Column(nullable = false, name = "debt_name")
    private String debtName;

    @Column(nullable = false, name = "total")
    private int total;

    @Column(nullable = false, name = "payment_amount")
    private int paymentAmount;

    public Debt() {

    }

    public Debt(User userId, String debtName, int total, int paymentAmount) {
        this.userId = userId;
        this.debtName = debtName;
        this.total = total;
        this.paymentAmount = paymentAmount;
    }

    public int getDebtId() {
        return debtId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
