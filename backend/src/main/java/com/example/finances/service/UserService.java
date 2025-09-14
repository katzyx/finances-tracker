package com.example.finances.service;

import com.example.finances.model.User;
import com.example.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service class for handling User-related business logic.
 * Designed for a single-user application.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the single user from the database.
     * Assumes there is only one user with ID 1.
     * @return The single User object.
     * @throws NoSuchElementException if the user with ID 1 is not found.
     */
    public User getSingleUser() {
        return userRepository.findById(1)
                .orElseThrow(() -> new NoSuchElementException("User with ID 1 not found."));
    }
}
