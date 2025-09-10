package com.example.finances.service;

import com.example.finances.model.*;
import com.example.finances.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction findByTransactionID(int transactionID) {
        return transactionRepository.findById(transactionID)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for ID: " + transactionID));
    }

    public List<Transaction> findByAccountID(Account accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for account: " + accountId));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for account: " + accountId);
        }
        return transactions;
    }

    public List<Transaction> findByDebtID(Debt debtId) {
        List<Transaction> transactions = transactionRepository.findByDebtId(debtId)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for debt: " + debtId));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for debt: " + debtId);
        }
        return transactions;
    }

    public List<Transaction> findByUserID(User userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for user: " + userId));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for user: " + userId);
        }
        return transactions;
    }

    public List<Transaction> findByCategoryId(Category categoryId) {
        List<Transaction> transactions = transactionRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for category: " + categoryId));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for category: " + categoryId);
        }
        return transactions;
    }

    public List<Transaction> findByTransactionDate(LocalDate transactionDate) {
        List<Transaction> transactions = transactionRepository.findByTransactionDate(transactionDate)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for date: " + transactionDate));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for date: " + transactionDate);
        }
        return transactions;
    }
}


