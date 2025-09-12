package com.example.finances.service;

import com.example.finances.model.*;
import com.example.finances.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private DebtRepository debtRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository, AccountRepository accountRepository, DebtRepository debtRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.debtRepository = debtRepository;
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction findByTransactionID(int transactionID) {
        return transactionRepository.findById(transactionID)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for ID: " + transactionID));
    }

    public List<Transaction> findByAccountID(int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found with ID: " + accountId));

        List<Transaction> transactions = transactionRepository.findByAccountId(account)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for account: " + account));

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for account: " + accountId);
        }
        return transactions;
    }

    public List<Transaction> findByDebtID(int debtId) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new NoSuchElementException("Debt not found with ID: " + debtId));

        List<Transaction> transactions = transactionRepository.findByDebtId(debt)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for debt: " + debt));

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for debt: " + debtId);
        }
        return transactions;
    }

    public List<Transaction> findByUserID(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        List<Transaction> transactions = transactionRepository.findByUserId(user)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for user: " + user));

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for user: " + userId);
        }
        return transactions;
    }

    public List<Transaction> findByCategoryId(int categoryId) {
        // use int id to get Category id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));

        List<Transaction> transactions = transactionRepository.findByCategoryId(category)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for category: " + category));

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


