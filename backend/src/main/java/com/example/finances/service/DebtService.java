package com.example.finances.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finances.dto.CreateDebtDTO;
import com.example.finances.model.Debt;
import com.example.finances.model.User;
import com.example.finances.repository.DebtRepository;
import com.example.finances.repository.UserRepository;

/**
 * Service class for handling Debt-related business logic.
 * Updated to handle simplified debt tracking with total owed vs amount paid.
 */
@Service
public class DebtService {
    private final DebtRepository debtRepository;
    private final UserRepository userRepository;

    @Autowired
    public DebtService(DebtRepository debtRepository, UserRepository userRepository) {
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all debts.
     * @return A list of all Debt objects.
     */
    public List<Debt> findAllDebts() {
        return debtRepository.findAll();
    }

    /**
     * Retrieves a single debt by its ID.
     * @param debtId The ID of the debt to find.
     * @return The Debt object.
     * @throws NoSuchElementException if the debt is not found.
     */
    public Debt findDebtById(int debtId) {
        return debtRepository.findById(debtId)
                .orElseThrow(() -> new NoSuchElementException("No debt found with ID: " + debtId));
    }

    /**
     * Finds all debts for a specific user.
     * @param userId The ID of the user.
     * @return A list of debts for the given user.
     * @throws NoSuchElementException if the user is not found.
     */
    public List<Debt> findDebtsByUserId(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        return debtRepository.findByUserId(user)
                .orElse(List.of()); // Return empty list instead of throwing exception
    }

    /**
     * Finds active debts (not fully paid off) for a user.
     * @param userId The ID of the user.
     * @return A list of active debts.
     */
    public List<Debt> findActiveDebtsByUserId(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        return debtRepository.findActiveDebtsByUser(user);
    }

    /**
     * Finds paid-off debts for a user.
     * @param userId The ID of the user.
     * @return A list of paid-off debts.
     */
    public List<Debt> findPaidOffDebtsByUserId(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        return debtRepository.findPaidOffDebtsByUser(user);
    }

    /**
     * Gets the total remaining debt for a user across all debts.
     * @param userId The ID of the user.
     * @return The total remaining debt amount.
     */
    public BigDecimal getTotalRemainingDebt(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        return debtRepository.getTotalRemainingDebtByUser(user)
                .orElse(BigDecimal.ZERO);
    }

    /**
     * Adds a new debt to the database.
     * @param debt The Debt object to be saved.
     * @return The saved Debt object.
     */
    public Debt addDebt(Debt debt) {
        // Ensure amount paid is not null and defaults to 0
        if (debt.getAmountPaid() == null) {
            debt.setAmountPaid(BigDecimal.ZERO);
        }

        // Validate that amount paid doesn't exceed total owed
        if (debt.getAmountPaid().compareTo(debt.getTotalOwed()) > 0) {
            throw new IllegalArgumentException("Amount paid cannot exceed total owed");
        }

        return debtRepository.save(debt);
    }

    public Debt addDebtFromDTO(CreateDebtDTO createDebtDTO) {
        // Find the User entity by userId from the DTO
        User user = userRepository.findById(createDebtDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + createDebtDTO.getUserId()));

        // Create a new Debt entity from the DTO data
        Debt newDebt = new Debt();
        newDebt.setUserId(user);
        newDebt.setDebtName(createDebtDTO.getDebtName());
        newDebt.setTotalOwed(createDebtDTO.getTotalOwed());
        newDebt.setMonthlyPayment(createDebtDTO.getMonthlyPayment());
        newDebt.setAmountPaid(createDebtDTO.getAmountPaid()); // Uses the default value from the DTO

        // Save the new Debt entity
        return debtRepository.save(newDebt);
    }

    /**
     * Updates an existing debt.
     * @param debtId The ID of the debt to update.
     * @param updatedDebt The updated Debt object.
     * @return The updated Debt object.
     * @throws NoSuchElementException if the debt to be updated is not found.
     */
    public Debt updateDebt(int debtId, Debt updatedDebt) {
        return debtRepository.findById(debtId)
                .map(debt -> {
                    debt.setUserId(updatedDebt.getUserId());
                    debt.setDebtName(updatedDebt.getDebtName());
                    debt.setTotalOwed(updatedDebt.getTotalOwed());
                    debt.setMonthlyPayment(updatedDebt.getMonthlyPayment());

                    // Ensure amount paid is not null and doesn't exceed total owed
                    BigDecimal newAmountPaid = updatedDebt.getAmountPaid() != null ?
                            updatedDebt.getAmountPaid() : BigDecimal.ZERO;

                    if (newAmountPaid.compareTo(updatedDebt.getTotalOwed()) > 0) {
                        throw new IllegalArgumentException("Amount paid cannot exceed total owed");
                    }

                    debt.setAmountPaid(newAmountPaid);
                    return debtRepository.save(debt);
                }).orElseThrow(() -> new NoSuchElementException("Cannot update. No debt found with ID: " + debtId));
    }

    /**
     * Makes a payment towards a debt, updating the amount paid.
     * @param debtId The ID of the debt to make payment on.
     * @param paymentAmount The amount of the payment.
     * @return The updated Debt object.
     * @throws NoSuchElementException if the debt is not found.
     * @throws IllegalArgumentException if payment amount is invalid.
     */
    public Debt makePayment(int debtId, BigDecimal paymentAmount) {
        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        Debt debt = findDebtById(debtId);
        BigDecimal newAmountPaid = debt.getAmountPaid().add(paymentAmount);

        if (newAmountPaid.compareTo(debt.getTotalOwed()) > 0) {
            throw new IllegalArgumentException("Payment would exceed total owed. Maximum payment: "
                    + debt.getRemainingBalance());
        }

        debt.setAmountPaid(newAmountPaid);
        return debtRepository.save(debt);
    }

    /**
     * Deletes a debt from the database.
     * @param debtId The ID of the debt to delete.
     * @throws NoSuchElementException if the debt to be deleted is not found.
     */
    public void deleteDebt(int debtId) {
        if (!debtRepository.existsById(debtId)) {
            throw new NoSuchElementException("Cannot delete. No debt found with ID: " + debtId);
        }
        debtRepository.deleteById(debtId);
    }
}