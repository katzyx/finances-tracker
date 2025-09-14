package com.example.finances.repository;

import com.example.finances.model.Debt;
import com.example.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
