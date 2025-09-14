package com.example.finances.service;

import com.example.finances.model.Category;
import com.example.finances.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class for handling Category-related business logic.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all categories from the database.
     * @return A list of all Category objects.
     */
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Finds a category by its ID.
     * @param categoryId The ID of the category to find.
     * @return The Category object.
     * @throws NoSuchElementException if the category is not found.
     */
    public Category findCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
    }

    /**
     * Finds a category by its name.
     * @param categoryName The name of the category to find.
     * @return The Category object.
     * @throws NoSuchElementException if the category is not found.
     */
    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new NoSuchElementException("Category not found with name: " + categoryName));
    }

    /**
     * Adds a new category to the database.
     * @param category The Category object to be saved.
     * @return The saved Category object.
     * @throws IllegalStateException if a category with the same name already exists.
     */
    public Category addCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new IllegalStateException("A category with the name '" + category.getCategoryName() + "' already exists.");
        }
        return categoryRepository.save(category);
    }

    /**
     * Updates an existing category.
     * @param categoryId The ID of the category to update.
     * @param updatedCategory The updated Category object.
     * @return The updated Category object.
     * @throws NoSuchElementException if the category to be updated is not found.
     */
    public Category updateCategory(int categoryId, Category updatedCategory) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    category.setCategoryName(updatedCategory.getCategoryName());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new NoSuchElementException("Cannot update. No category found with ID: " + categoryId));
    }

    /**
     * Deletes a category from the database.
     * @param categoryId The ID of the category to delete.
     * @throws NoSuchElementException if the category to be deleted is not found.
     */
    public void deleteCategory(int categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("Cannot delete. No category found with ID: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
