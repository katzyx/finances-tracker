package com.example.finances.controller;

import com.example.finances.model.Debt;
import com.example.finances.service.DebtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for the Debt entity.
 * Provides endpoints for managing debts.
 */
@RestController
@CrossOrigin
@RequestMapping("/debts")
public class DebtController {
    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    /**
     * Retrieves all debts.
     * @return A ResponseEntity containing a list of all Debt objects.
     */
    @GetMapping
    public ResponseEntity<List<Debt>> getAllDebts() {
        List<Debt> debts = debtService.findAllDebts();
        return ResponseEntity.ok(debts);
    }

    /**
     * Finds a debt by its ID.
     * @param debtId The ID of the debt.
     * @return A ResponseEntity containing the Debt object or a NOT_FOUND status.
     */
    @GetMapping("/{debtId}")
    public ResponseEntity<Debt> findByDebtId(@PathVariable int debtId) {
        try {
            Debt debt = debtService.findDebtById(debtId);
            return ResponseEntity.ok(debt);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Finds all debts for a specific user.
     * @param userId The ID of the user.
     * @return A ResponseEntity containing a list of debts for the given user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Debt>> findDebtsByUserId(@PathVariable int userId) {
        try {
            List<Debt> debts = debtService.findDebtsByUserId(userId);
            return ResponseEntity.ok(debts);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Adds a new debt.
     * @param debt The Debt object to be added.
     * @return A ResponseEntity containing the newly created Debt object and a CREATED status.
     */
    @PostMapping
    public ResponseEntity<Debt> addDebt(@RequestBody Debt debt) {
        Debt newDebt = debtService.addDebt(debt);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDebt);
    }

    /**
     * Updates an existing debt.
     * @param debtId The ID of the debt to update.
     * @param updatedDebt The updated Debt object.
     * @return A ResponseEntity containing the updated Debt object or a NOT_FOUND status.
     */
    @PutMapping("/{debtId}")
    public ResponseEntity<Debt> updateDebt(@PathVariable int debtId, @RequestBody Debt updatedDebt) {
        try {
            Debt debt = debtService.updateDebt(debtId, updatedDebt);
            return ResponseEntity.ok(debt);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a debt.
     * @param debtId The ID of the debt to delete.
     * @return A ResponseEntity with a NO_CONTENT status if successful, or NOT_FOUND if the debt does not exist.
     */
    @DeleteMapping("/{debtId}")
    public ResponseEntity<Void> deleteDebt(@PathVariable int debtId) {
        try {
            debtService.deleteDebt(debtId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
