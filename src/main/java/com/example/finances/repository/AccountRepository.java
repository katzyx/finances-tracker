package com.example.finances.repository;

import com.example.finances.model.Account;
import com.example.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<List<Account>> findByUserId(User userId);
    Optional<Account> findByAccountName(String accountName);
}
