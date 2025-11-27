package com.ims.inventorymanagementsystem.service;

import com.ims.inventorymanagementsystem.exception.ResourceNotFoundException;
import com.ims.inventorymanagementsystem.model.Product;
import com.ims.inventorymanagementsystem.model.StockMovement;
import com.ims.inventorymanagementsystem.model.User;
import com.ims.inventorymanagementsystem.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductService productService;
    private final UserService userService;

    public List<StockMovement> getAllMovements() {
        log.info("Fetching all stock movements");
        return stockMovementRepository.findAll();
    }

    public List<StockMovement> getRecentMovements() {
        log.info("Fetching recent stock movements");
        return stockMovementRepository.findTop10ByOrderByMovementDateDesc();
    }

    public List<StockMovement> getMovementsByProductId(Long productId) {
        log.info("Fetching stock movements for product id: {}", productId);
        return stockMovementRepository.findByProductId(productId);
    }

    public List<StockMovement> getMovementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching stock movements between {} and {}", startDate, endDate);
        return stockMovementRepository.findByDateRange(startDate, endDate);
    }

    public StockMovement createMovement(StockMovement movement) {
        log.info("Creating new stock movement for product id: {}", movement.getProduct().getId());

        Product product = productService.getProductById(movement.getProduct().getId());

        // Automatically set the current user as performedBy if not set
        if (movement.getPerformedBy() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()
                    && !"anonymousUser".equals(authentication.getPrincipal())) {
                String username = authentication.getName();
                User currentUser = userService.getUserByUsername(username);
                movement.setPerformedBy(currentUser);
                log.info("Stock movement will be performed by: {}", username);
            }
        }

        // Update product quantity based on movement type
        int newQuantity = product.getQuantity();
        switch (movement.getType()) {
            case IN, RETURN -> newQuantity += movement.getQuantity();
            case OUT -> {
                newQuantity -= movement.getQuantity();
                if (newQuantity < 0) {
                    throw new IllegalArgumentException("Insufficient stock for this operation");
                }
            }
            case ADJUSTMENT -> newQuantity = movement.getQuantity();
        }

        productService.updateProductQuantity(product.getId(), newQuantity);

        StockMovement savedMovement = stockMovementRepository.save(movement);
        log.info("Stock movement created successfully with id: {}", savedMovement.getId());
        return savedMovement;
    }

    public StockMovement getMovementById(Long id) {
        log.info("Fetching stock movement with id: {}", id);
        return stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StockMovement", "id", id));
    }
}
