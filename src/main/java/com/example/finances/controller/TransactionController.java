package com.example.finances.controller;

import com.example.finances.model.*;
import com.example.finances.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionID}")
    public ResponseEntity<Transaction> findByTransactionID(@PathVariable int transactionID) {
        try {
            Transaction transaction = transactionService.findByTransactionID(transactionID);
            return ResponseEntity.ok(transaction);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("account/{accountId}")
    public ResponseEntity<List<Transaction>> findByAccountID(@PathVariable Account accountId) {
        try {
            List<Transaction> transaction = transactionService.findByAccountID(accountId);
            return ResponseEntity.ok(transaction);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("debt/{debtId}")
    public ResponseEntity<List<Transaction>> findByDebtID(@PathVariable Debt debtId) {
        try {
            List<Transaction> transactions = transactionService.findByDebtID(debtId);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Transaction>> findByUserId(@PathVariable User userId) {
        try {
            List<Transaction> transactions = transactionService.findByUserID(userId);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<Transaction>> findByCategoryID(@PathVariable Category categoryId) {
        try {
            List<Transaction> transactions = transactionService.findByCategoryId(categoryId);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/date/{transactionDate}")
    public ResponseEntity<List<Transaction>> findByTransactionDate(@PathVariable LocalDate transactionDate) {
        try {
            List<Transaction> transactions = transactionService.findByTransactionDate(transactionDate);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
