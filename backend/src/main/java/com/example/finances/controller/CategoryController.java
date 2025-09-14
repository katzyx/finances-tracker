package com.example.finances.controller;

import com.example.finances.model.Category;
import com.example.finances.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for the Category entity.
 * Provides endpoints for managing categories.
 */
@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves all categories.
     * @return A ResponseEntity containing a list of all Category objects.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Finds a category by its ID.
     * @param categoryId The ID of the category.
     * @return A ResponseEntity containing the Category object or a NOT_FOUND status.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findByCategoryId(@PathVariable int categoryId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Finds a category by its name.
     * @param categoryName The name of the category.
     * @return A ResponseEntity containing the Category object or a NOT_FOUND status.
     */
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Category> findByCategoryName(@PathVariable String categoryName) {
        try {
            Category category = categoryService.findCategoryByName(categoryName);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Adds a new category.
     * @param category The Category object to be added.
     * @return A ResponseEntity containing the newly created Category object and a CREATED status,
     * or a CONFLICT status if the category already exists.
     */
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            Category newCategory = categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Updates an existing category.
     * @param categoryId The ID of the category to update.
     * @param updatedCategory The updated Category object.
     * @return A ResponseEntity containing the updated Category object or a NOT_FOUND status.
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable int categoryId, @RequestBody Category updatedCategory) {
        try {
            Category category = categoryService.updateCategory(categoryId, updatedCategory);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a category.
     * @param categoryId The ID of the category to delete.
     * @return A ResponseEntity with a NO_CONTENT status if successful, or NOT_FOUND if the category does not exist.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
