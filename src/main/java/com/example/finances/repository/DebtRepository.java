package com.example.finances.repository;

import com.example.finances.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Integer> {
}
