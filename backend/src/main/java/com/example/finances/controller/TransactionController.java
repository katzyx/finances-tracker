package com.example.finances.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finances.dto.CreateTransactionDTO;
import com.example.finances.dto.TransactionResponseDTO;
import com.example.finances.model.Transaction;
import com.example.finances.service.TransactionService;

import jakarta.validation.Valid;

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
    public ResponseEntity<List<Transaction>> findByAccountID(@PathVariable int accountId) {
        try {
            List<Transaction> transaction = transactionService.findByAccountID(accountId);
            return ResponseEntity.ok(transaction);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("debt/{debtId}")
    public ResponseEntity<List<Transaction>> findByDebtID(@PathVariable int debtId) {
        try {
            List<Transaction> transactions = transactionService.findByDebtID(debtId);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Transaction>> findByUserId(@PathVariable int userId) {
        try {
            List<Transaction> transactions = transactionService.findByUserID(userId);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<Transaction>> findByCategoryID(@PathVariable int categoryId) {
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

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> findByType(@PathVariable String type) {
        try {
            List<Transaction> transactions = transactionService.findByType(type);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/recurrence/{recurrence}")
    public ResponseEntity<List<Transaction>> findByRecurrence(@PathVariable String recurrence) {
        try {
            List<Transaction> transactions = transactionService.findByRecurrence(recurrence);
            return ResponseEntity.ok(transactions);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(createTransactionDTO);
            TransactionResponseDTO responseDTO = new TransactionResponseDTO(createdTransaction);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            // This will now catch the serialization error and can be logged or handled
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int transactionId, @RequestBody Transaction transactionDetails) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(transactionId, transactionDetails);
            return ResponseEntity.ok(updatedTransaction);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
