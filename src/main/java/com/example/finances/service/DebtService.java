package com.example.finances.service;

import com.example.finances.model.Debt;
import com.example.finances.model.User;
import com.example.finances.repository.DebtRepository;
import com.example.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for handling Debt-related business logic.
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
     * @throws NoSuchElementException if the user or any debts for that user are not found.
     */
    public List<Debt> findDebtsByUserId(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        List<Debt> debts = debtRepository.findByUserId(user)
                .orElseThrow(() -> new NoSuchElementException("No debts found for user with ID: " + userId));

        return debts;
    }

    /**
     * Adds a new debt to the database.
     * @param debt The Debt object to be saved.
     * @return The saved Debt object.
     */
    public Debt addDebt(Debt debt) {
        return debtRepository.save(debt);
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
                    debt.setTotal(updatedDebt.getTotal());
                    debt.setPaymentAmount(updatedDebt.getPaymentAmount());
                    return debtRepository.save(debt);
                }).orElseThrow(() -> new NoSuchElementException("Cannot update. No debt found with ID: " + debtId));
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
