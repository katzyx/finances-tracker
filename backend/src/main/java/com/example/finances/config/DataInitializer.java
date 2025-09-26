package com.example.finances.config;

import com.example.finances.model.Account;
import com.example.finances.model.Category;
import com.example.finances.model.User;
import com.example.finances.repository.AccountRepository;
import com.example.finances.repository.CategoryRepository;
import com.example.finances.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void initData() {
        System.out.println("Checking if sample data needs to be initialized...");
        
        // Check if data already exists
        if (userRepository.count() == 0) {
            System.out.println("No users found. Initializing sample data...");
            
            try {
                // Create user
                User user = new User();
                user = userRepository.save(user);
                System.out.println("Created user with ID: " + user.getUserId());
                
                // Create account
                Account account = new Account(user, "Chequing", BigDecimal.ZERO);
                account = accountRepository.save(account);
                System.out.println("Created account: " + account.getAccountName());
                
                // Create categories
                String[] categoryNames = {"Other", "Rent", "Groceries", "Shopping", "Transportation", "Restaurants"};
                for (String name : categoryNames) {
                    Category category = new Category(name);
                    category = categoryRepository.save(category);
                    System.out.println("Created category: " + category.getCategoryName() + " with ID: " + category.getCategoryId());
                }
                
                System.out.println("Sample data initialized successfully!");
                System.out.println("Total users: " + userRepository.count());
                System.out.println("Total accounts: " + accountRepository.count());
                System.out.println("Total categories: " + categoryRepository.count());
                
            } catch (Exception e) {
                System.err.println("Error initializing sample data: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Sample data already exists. Skipping initialization.");
            System.out.println("Total users: " + userRepository.count());
            System.out.println("Total accounts: " + accountRepository.count());
            System.out.println("Total categories: " + categoryRepository.count());
        }
    }
}