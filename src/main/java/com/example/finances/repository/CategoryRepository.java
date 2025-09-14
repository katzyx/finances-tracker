package com.example.finances.repository;

import com.example.finances.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for the Category entity.
 * This handles data access operations for the 'categories' table.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /**
     * Finds a category by its name.
     * @param categoryName The name of the category to find.
     * @return An Optional containing the Category object if found, otherwise an empty Optional.
     */
    Optional<Category> findByCategoryName(String categoryName);
}
