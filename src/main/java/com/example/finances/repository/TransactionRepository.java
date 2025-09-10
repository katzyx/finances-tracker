package com.example.finances.repository;

import com.example.finances.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<List<Transaction>> findByAccountId(Integer accountId);
    Optional<List<Transaction>> findByDebtId(Integer debtId);
    Optional<List<Transaction>> findByUserId(Integer userId);
    Optional<List<Transaction>> findByCategoryId(Integer categoryId);
    Optional<List<Transaction>> findByTransactionDate(LocalDate transactionDate);
}
