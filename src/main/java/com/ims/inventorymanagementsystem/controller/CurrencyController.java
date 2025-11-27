package com.ims.inventorymanagementsystem.controller;

import com.ims.inventorymanagementsystem.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    /**
     * External API Integration - Get current exchange rates
     * This endpoint demonstrates integration with external web service
     */
    @GetMapping("/rates")
    public ResponseEntity<Map<String, Object>> getExchangeRates() {
        Map<String, Object> rates = currencyService.getExchangeRates();
        return ResponseEntity.ok(rates);
    }

    /**
     * Convert price from USD to target currency
     */
    @GetMapping("/convert")
    public ResponseEntity<Map<String, Object>> convertPrice(
            @RequestParam BigDecimal amount,
            @RequestParam String targetCurrency) {

        BigDecimal convertedAmount = currencyService.convertPrice(amount, targetCurrency);

        return ResponseEntity.ok(Map.of(
                "originalAmount", amount,
                "originalCurrency", "USD",
                "convertedAmount", convertedAmount,
                "targetCurrency", targetCurrency
        ));
    }

    /**
     * Get list of available currencies
     */
    @GetMapping("/currencies")
    public ResponseEntity<java.util.Set<String>> getAvailableCurrencies() {
        java.util.Set<String> currencies = currencyService.getAvailableCurrencies();
        return ResponseEntity.ok(currencies);
    }
}
