package com.ims.inventorymanagementsystem.controller;

import com.ims.inventorymanagementsystem.model.StockMovement;
import com.ims.inventorymanagementsystem.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<List<StockMovement>> getAllMovements() {
        List<StockMovement> movements = stockMovementService.getAllMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<StockMovement>> getRecentMovements() {
        List<StockMovement> movements = stockMovementService.getRecentMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovement> getMovementById(@PathVariable Long id) {
        StockMovement movement = stockMovementService.getMovementById(id);
        return ResponseEntity.ok(movement);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovement>> getMovementsByProduct(@PathVariable Long productId) {
        List<StockMovement> movements = stockMovementService.getMovementsByProductId(productId);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<StockMovement>> getMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<StockMovement> movements = stockMovementService.getMovementsByDateRange(startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @PostMapping
    public ResponseEntity<StockMovement> createMovement(@Valid @RequestBody StockMovement movement) {
        StockMovement createdMovement = stockMovementService.createMovement(movement);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }
}
