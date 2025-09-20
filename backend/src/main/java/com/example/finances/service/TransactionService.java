package com.example.finances.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finances.dto.CreateTransactionDTO;
import com.example.finances.model.Account;
import com.example.finances.model.Category;
import com.example.finances.model.Debt;
import com.example.finances.model.Transaction;
import com.example.finances.model.User;
import com.example.finances.repository.AccountRepository;
import com.example.finances.repository.CategoryRepository;
import com.example.finances.repository.DebtRepository;
import com.example.finances.repository.TransactionRepository;
import com.example.finances.repository.UserRepository;

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
                .orElse(null);

        // Return an empty list if no transactions are found, instead of throwing an exception
        if (transactions == null || transactions.isEmpty()) {
            return List.of();
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
    public Transaction createTransaction(CreateTransactionDTO dto) {
        // Find related entities by their IDs, throwing NoSuchElementException if not found.
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + dto.getUserId()));

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("Account not found with ID: " + dto.getAccountId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + dto.getCategoryId()));

        Debt debt = null;
        if (dto.getDebtId() != null) {
            debt = debtRepository.findById(dto.getDebtId())
                    .orElseThrow(() -> new NoSuchElementException("Debt not found with ID: " + dto.getDebtId()));
        }

        // Create the new transaction entity with the retrieved objects
        Transaction transaction = new Transaction();
        transaction.setUserId(user);
        transaction.setAccountId(account);
        transaction.setCategoryId(category);
        transaction.setDebtId(debt);
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setType(dto.getType());
        transaction.setRecurrence(dto.getRecurrence());
        transaction.setTransactionDate(LocalDate.now());

        // Save and return the new transaction
        return transactionRepository.save(transaction);
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