package com.ims.inventorymanagementsystem.service;

import com.ims.inventorymanagementsystem.exception.DuplicateResourceException;
import com.ims.inventorymanagementsystem.exception.ResourceNotFoundException;
import com.ims.inventorymanagementsystem.model.Category;
import com.ims.inventorymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        log.info("Fetching active categories");
        return categoryRepository.findByIsActiveTrue();
    }

    public Category getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public Category createCategory(Category category) {
        log.info("Creating new category: {}", category.getName());

        if (categoryRepository.existsByName(category.getName())) {
            throw new DuplicateResourceException("Category", "name", category.getName());
        }

        Category savedCategory = categoryRepository.save(category);
        log.info("Category created successfully with id: {}", savedCategory.getId());
        return savedCategory;
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        log.info("Updating category with id: {}", id);

        Category category = getCategoryById(id);

        if (!category.getName().equals(categoryDetails.getName())
                && categoryRepository.existsByName(categoryDetails.getName())) {
            throw new DuplicateResourceException("Category", "name", categoryDetails.getName());
        }

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setIsActive(categoryDetails.getIsActive());

        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated successfully");
        return updatedCategory;
    }

    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        log.info("Category deleted successfully");
    }
}
