package com.example.finances.service;

import com.example.finances.dto.CreateTransactionDTO;
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
                .orElseThrow(() -> new NoSuchElementException("No account found with ID: " + accountId));

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

    public List<Transaction> findByType(String type) {
        List<Transaction> transactions = transactionRepository.findByType(type)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for type: " + type));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for type: " + type);
        }
        return transactions;
    }

    public List<Transaction> findByRecurrence(String recurrence) {
        List<Transaction> transactions = transactionRepository.findByRecurrence(recurrence)
                .orElseThrow(() -> new NoSuchElementException("No transactions found for recurrence: " + recurrence));
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for recurrence: " + recurrence);
        }
        return transactions;
    }

    /**
     * Creates a new transaction.
     * @param createTransactionDTO The DTO containing the transaction details.
     * @return The created Transaction object.
     * @throws NoSuchElementException if a related entity (Account, User, Category, or Debt) is not found.
     */
    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO) {
        Account account = accountRepository.findById(createTransactionDTO.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("Account not found with ID: " + createTransactionDTO.getAccountId()));
        User user = userRepository.findById(createTransactionDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + createTransactionDTO.getUserId()));
        Category category = categoryRepository.findById(createTransactionDTO.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + createTransactionDTO.getCategoryId()));

        Debt debt = null;
        Integer debtId = createTransactionDTO.getDebtId();
        // Check if debtId is valid (not null and a positive number) before trying to find it
        if (debtId != null && debtId > 0) {
            debt = debtRepository.findById(debtId)
                    .orElseThrow(() -> new NoSuchElementException("Debt not found with ID: " + debtId));
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAccountId(account);
        newTransaction.setUserId(user);
        newTransaction.setAmount(createTransactionDTO.getAmount());
        newTransaction.setDescription(createTransactionDTO.getDescription());
        newTransaction.setCategoryId(category);
        newTransaction.setDebtId(debt);
        newTransaction.setTransactionDate(LocalDate.now());
        newTransaction.setType(createTransactionDTO.getType());

        // Handle null/empty recurrence - store as null in database if empty
        String recurrence = createTransactionDTO.getRecurrence();
        if (recurrence != null && recurrence.trim().isEmpty()) {
            recurrence = null;
        }
        newTransaction.setRecurrence(recurrence);

        return transactionRepository.save(newTransaction);
    }

    /**
     * Updates an existing transaction.
     * @param transactionId The ID of the transaction to update.
     * @param transactionDetails The updated details of the transaction.
     * @return The updated Transaction object.
     * @throws NoSuchElementException if the transaction is not found.
     */
    public Transaction updateTransaction(int transactionId, Transaction transactionDetails) {
        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));

        existingTransaction.setAccountId(transactionDetails.getAccountId());
        existingTransaction.setUserId(transactionDetails.getUserId());
        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setDescription(transactionDetails.getDescription());
        existingTransaction.setCategoryId(transactionDetails.getCategoryId());
        existingTransaction.setDebtId(transactionDetails.getDebtId());
        existingTransaction.setTransactionDate(transactionDetails.getTransactionDate());
        existingTransaction.setType(transactionDetails.getType());
        existingTransaction.setRecurrence(transactionDetails.getRecurrence());

        return transactionRepository.save(existingTransaction);
    }

    /**
     * Deletes a transaction by its ID.
     * @param transactionId The ID of the transaction to delete.
     * @throws NoSuchElementException if the transaction is not found.
     */
    public void deleteTransaction(int transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new NoSuchElementException("Transaction not found with ID: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }
}