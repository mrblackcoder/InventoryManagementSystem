package com.ims.inventorymanagementsystem.service;

import com.ims.inventorymanagementsystem.exception.ResourceNotFoundException;
import com.ims.inventorymanagementsystem.model.Supplier;
import com.ims.inventorymanagementsystem.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        log.info("Fetching all suppliers");
        return supplierRepository.findAll();
    }

    public List<Supplier> getActiveSuppliers() {
        log.info("Fetching active suppliers");
        return supplierRepository.findByIsActiveTrue();
    }

    public Supplier getSupplierById(Long id) {
        log.info("Fetching supplier with id: {}", id);
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
    }

    public List<Supplier> searchSuppliers(String name) {
        log.info("Searching suppliers with name containing: {}", name);
        return supplierRepository.findByNameContainingIgnoreCase(name);
    }

    public Supplier createSupplier(Supplier supplier) {
        log.info("Creating new supplier: {}", supplier.getName());
        Supplier savedSupplier = supplierRepository.save(supplier);
        log.info("Supplier created successfully with id: {}", savedSupplier.getId());
        return savedSupplier;
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        log.info("Updating supplier with id: {}", id);

        Supplier supplier = getSupplierById(id);

        supplier.setName(supplierDetails.getName());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setPhone(supplierDetails.getPhone());
        supplier.setAddress(supplierDetails.getAddress());
        supplier.setIsActive(supplierDetails.getIsActive());

        Supplier updatedSupplier = supplierRepository.save(supplier);
        log.info("Supplier updated successfully");
        return updatedSupplier;
    }

    public void deleteSupplier(Long id) {
        log.info("Deleting supplier with id: {}", id);
        Supplier supplier = getSupplierById(id);
        supplierRepository.delete(supplier);
        log.info("Supplier deleted successfully");
    }
}
