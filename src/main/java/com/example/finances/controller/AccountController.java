package com.example.finances.controller;

import com.example.finances.dto.CreateAccountDTO;
import com.example.finances.model.Account;
import com.example.finances.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> findByAccountId(@PathVariable int accountId) {
        try {
            Account account = accountService.findAccountById(accountId);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> findByUserId(@PathVariable int userId) {
        try {
            List<Account> accounts = accountService.findAccountByUserId(userId);
            return ResponseEntity.ok(accounts);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/name/{accountName}")
    public ResponseEntity<Account> findByAccountName(@PathVariable String accountName) {
        try {
            Account account = accountService.findByAccountName(accountName);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) {
        try {
            Account createdAccount = accountService.createAccount(createAccountDTO);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable int accountId, @RequestBody Account accountDetails) {
        try {
            Account updatedAccount = accountService.updateAccount(accountId, accountDetails);
            return ResponseEntity.ok(updatedAccount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}