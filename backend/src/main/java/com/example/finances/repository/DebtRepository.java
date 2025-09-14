package com.example.finances.repository;

import com.example.finances.model.Debt;
import com.example.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the Debt entity.
 * This handles data access operations for the 'debts' table.
 */
@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    /**
     * Finds all debts associated with a specific user.
     * @param userId The User object to search for.
     * @return An Optional containing a list of Debt objects if found, otherwise an empty Optional.
     */
    Optional<List<Debt>> findByUserId(User userId);

    /**
     * Finds debts by name (case-insensitive partial match).
     * @param debtName The name to search for.
     * @return List of matching debts.
     */
    List<Debt> findByDebtNameContainingIgnoreCase(String debtName);

    /**
     * Finds all debts that are fully paid off (amount_paid >= total_owed).
     * @param userId The user to search for.
     * @return List of paid-off debts.
     */
    @Query("SELECT d FROM Debt d WHERE d.userId = :user AND d.amountPaid >= d.totalOwed")
    List<Debt> findPaidOffDebtsByUser(@Param("user") User userId);

    /**
     * Finds all active debts (amount_paid < total_owed).
     * @param userId The user to search for.
     * @return List of active debts.
     */
    @Query("SELECT d FROM Debt d WHERE d.userId = :user AND d.amountPaid < d.totalOwed")
    List<Debt> findActiveDebtsByUser(@Param("user") User userId);

    /**
     * Gets total amount owed by a user across all debts.
     * @param userId The user to calculate for.
     * @return Total remaining balance across all debts.
     */
    @Query("SELECT SUM(d.totalOwed - d.amountPaid) FROM Debt d WHERE d.userId = :user AND d.amountPaid < d.totalOwed")
    Optional<BigDecimal> getTotalRemainingDebtByUser(@Param("user") User userId);
}