package com.example.finances.repository;

import com.example.finances.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<List<Transaction>> findByAccountId(Account accountId);
    Optional<List<Transaction>> findByDebtId(Debt debtId);
    Optional<List<Transaction>> findByUserId(User userId);
    Optional<List<Transaction>> findByCategoryId(Category categoryId);
    Optional<List<Transaction>> findByTransactionDate(LocalDate transactionDate);
    Optional<List<Transaction>> findByType(String type);
    Optional<List<Transaction>> findByRecurrence(String recurrence);
}
