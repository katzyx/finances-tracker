package com.example.finances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "debts")
@Getter
@Setter
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_id")
    private int debtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User userId;

    @Column(nullable = false, name = "debt_name")
    private String debtName;

    @Column(nullable = false, name = "total_owed")
    private BigDecimal totalOwed;

    @Column(nullable = false, name = "amount_paid")
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(nullable = false, name = "monthly_payment")
    private BigDecimal monthlyPayment;

    public Debt() {
    }

    public Debt(User userId, String debtName, BigDecimal totalOwed,
                BigDecimal amountPaid, BigDecimal monthlyPayment) {
        this.userId = userId;
        this.debtName = debtName;
        this.totalOwed = totalOwed;
        this.amountPaid = amountPaid;
        this.monthlyPayment = monthlyPayment;
    }

    // Calculated field - remaining balance
    public BigDecimal getRemainingBalance() {
        return totalOwed.subtract(amountPaid);
    }

    // Calculated field - progress percentage
    public double getPaymentProgress() {
        if (totalOwed.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return amountPaid.divide(totalOwed, 4, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }
}