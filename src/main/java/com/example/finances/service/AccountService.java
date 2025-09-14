package com.example.finances.service;

import com.example.finances.dto.CreateAccountDTO;
import com.example.finances.model.Account;
import com.example.finances.model.User;
import com.example.finances.repository.AccountRepository;
import com.example.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccountById(int accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No account found with ID: " + accountId));
    }

    public List<Account> findAccountByUserId(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found for ID: " + userId));

        return accountRepository.findByUserId(user)
                .orElseThrow(() -> new NoSuchElementException("No accounts found for user: " + user));
    }

    public Account findByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new NoSuchElementException("No account found for name: " + accountName));
    }

    public Account createAccount(CreateAccountDTO createAccountDTO) {
        // Step 1: Find the existing User entity by ID
        User user = userRepository.findById(createAccountDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + createAccountDTO.getUserId()));

        // Step 2: Create a new Account entity and link it to the managed User
        Account newAccount = new Account();
        newAccount.setUserId(user);
        newAccount.setAccountName(createAccountDTO.getAccountName());
        newAccount.setAccountBalance(createAccountDTO.getAccountBalance());

        // Step 3: Save the new Account
        return accountRepository.save(newAccount);
    }

    public Account updateAccount(int accountId, Account updatedAccount) {
        // Find the existing account or throw an exception if not found
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found with ID: " + accountId));

        // Update the account details
        existingAccount.setAccountName(updatedAccount.getAccountName());
        existingAccount.setAccountBalance(updatedAccount.getAccountBalance());
        existingAccount.setUserId(updatedAccount.getUserId());

        // Save and return the updated account
        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(int accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new NoSuchElementException("Account not found with ID: " + accountId);
        }
        accountRepository.deleteById(accountId);
    }
}