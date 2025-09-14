package com.example.finances.controller;

import com.example.finances.model.User;
import com.example.finances.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

/**
 * REST controller for the User entity.
 * Provides a single endpoint to retrieve the single user.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve the single user.
     * @return A ResponseEntity containing the User object or a NOT_FOUND status.
     */
    @GetMapping("/1")
    public ResponseEntity<User> getSingleUser() {
        try {
            User user = userService.getSingleUser();
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
