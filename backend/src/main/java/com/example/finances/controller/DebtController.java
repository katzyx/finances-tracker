package com.example.finances.controller;

import java.math.BigDecimal;
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

import com.example.finances.dto.CreateDebtDTO;
import com.example.finances.model.Debt;
import com.example.finances.service.DebtService;

import jakarta.validation.Valid;

/**
 * REST controller for the Debt entity.
 * Updated to handle simplified debt tracking with payment progress.
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
     * Finds active debts (not fully paid off) for a specific user.
     * @param userId The ID of the user.
     * @return A ResponseEntity containing a list of active debts.
     */
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Debt>> findActiveDebtsByUserId(@PathVariable int userId) {
        try {
            List<Debt> activeDebts = debtService.findActiveDebtsByUserId(userId);
            return ResponseEntity.ok(activeDebts);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Finds paid-off debts for a specific user.
     * @param userId The ID of the user.
     * @return A ResponseEntity containing a list of paid-off debts.
     */
    @GetMapping("/user/{userId}/paid-off")
    public ResponseEntity<List<Debt>> findPaidOffDebtsByUserId(@PathVariable int userId) {
        try {
            List<Debt> paidOffDebts = debtService.findPaidOffDebtsByUserId(userId);
            return ResponseEntity.ok(paidOffDebts);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Gets the total remaining debt for a user across all debts.
     * @param userId The ID of the user.
     * @return A ResponseEntity containing the total remaining debt amount.
     */
    @GetMapping("/user/{userId}/total-remaining")
    public ResponseEntity<BigDecimal> getTotalRemainingDebt(@PathVariable int userId) {
        try {
            BigDecimal totalRemaining = debtService.getTotalRemainingDebt(userId);
            return ResponseEntity.ok(totalRemaining);
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
    public ResponseEntity<?> addDebt(@Valid @RequestBody CreateDebtDTO createDebtDTO) {
        try {
            Debt newDebt = debtService.addDebtFromDTO(createDebtDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDebt);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    /**
     * Updates an existing debt.
     * @param debtId The ID of the debt to update.
     * @param updatedDebt The updated Debt object.
     * @return A ResponseEntity containing the updated Debt object or a NOT_FOUND status.
     */
    @PutMapping("/{debtId}")
    public ResponseEntity<?> updateDebt(@PathVariable int debtId, @Valid @RequestBody Debt updatedDebt) {
        try {
            Debt debt = debtService.updateDebt(debtId, updatedDebt);
            return ResponseEntity.ok(debt);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Makes a payment towards a specific debt.
     * @param debtId The ID of the debt to make payment on.
     * @param paymentRequest Object containing payment amount.
     * @return A ResponseEntity containing the updated Debt object.
     */
    @PostMapping("/{debtId}/payment")
    public ResponseEntity<?> makePayment(@PathVariable int debtId, @RequestBody PaymentRequest paymentRequest) {
        try {
            Debt updatedDebt = debtService.makePayment(debtId, paymentRequest.getPaymentAmount());
            return ResponseEntity.ok(updatedDebt);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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

    /**
     * Inner class for payment requests.
     */
    public static class PaymentRequest {
        private BigDecimal paymentAmount;

        public PaymentRequest() {}

        public PaymentRequest(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public BigDecimal getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
        }
    }
}