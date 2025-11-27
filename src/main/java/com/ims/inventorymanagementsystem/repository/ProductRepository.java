package com.ims.inventorymanagementsystem.repository;

import com.ims.inventorymanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByStatus(Product.ProductStatus status);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE p.quantity <= p.reorderLevel AND p.status = 'ACTIVE'")
    List<Product> findLowStockProducts();

    boolean existsBySku(String sku);
}
