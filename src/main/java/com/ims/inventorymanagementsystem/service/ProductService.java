package com.ims.inventorymanagementsystem.service;

import com.ims.inventorymanagementsystem.exception.DuplicateResourceException;
import com.ims.inventorymanagementsystem.exception.ResourceNotFoundException;
import com.ims.inventorymanagementsystem.model.Product;
import com.ims.inventorymanagementsystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public Product getProductBySku(String sku) {
        log.info("Fetching product with SKU: {}", sku);
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "SKU", sku));
    }

    public List<Product> searchProductsByName(String name) {
        log.info("Searching products with name containing: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByStatus(Product.ProductStatus status) {
        log.info("Fetching products with status: {}", status);
        return productRepository.findByStatus(status);
    }

    public List<Product> getLowStockProducts() {
        log.info("Fetching low stock products");
        return productRepository.findLowStockProducts();
    }

    public Product createProduct(Product product) {
        log.info("Creating new product with SKU: {}", product.getSku());

        if (productRepository.existsBySku(product.getSku())) {
            throw new DuplicateResourceException("Product", "SKU", product.getSku());
        }

        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with id: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(Long id, Product productDetails) {
        log.info("Updating product with id: {}", id);

        Product product = getProductById(id);

        // Check if SKU is being changed and if it already exists
        if (!product.getSku().equals(productDetails.getSku())
                && productRepository.existsBySku(productDetails.getSku())) {
            throw new DuplicateResourceException("Product", "SKU", productDetails.getSku());
        }

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setSku(productDetails.getSku());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setReorderLevel(productDetails.getReorderLevel());
        product.setStatus(productDetails.getStatus());

        // Update category if provided
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }

        // Update supplier if provided
        if (productDetails.getSupplier() != null) {
            product.setSupplier(productDetails.getSupplier());
        }

        Product updatedProduct = productRepository.save(product);
        log.info("Product updated successfully with id: {}", updatedProduct.getId());
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Product deleted successfully with id: {}", id);
    }

    public Product updateProductQuantity(Long id, Integer quantity) {
        log.info("Updating quantity for product id: {} to {}", id, quantity);

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        Product product = getProductById(id);
        product.setQuantity(quantity);

        Product updatedProduct = productRepository.save(product);
        log.info("Product quantity updated successfully");
        return updatedProduct;
    }

    public Product adjustProductQuantity(Long id, Integer adjustment) {
        log.info("Adjusting quantity for product id: {} by {}", id, adjustment);

        Product product = getProductById(id);
        int newQuantity = product.getQuantity() + adjustment;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Resulting quantity cannot be negative");
        }

        product.setQuantity(newQuantity);

        Product updatedProduct = productRepository.save(product);
        log.info("Product quantity adjusted successfully. New quantity: {}", newQuantity);
        return updatedProduct;
    }
}
