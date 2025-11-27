package com.ims.inventorymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";

    /**
     * Fetches exchange rates from external API
     * This demonstrates external web service integration requirement
     */
    public Map<String, Object> getExchangeRates() {
        try {
            log.info("Fetching exchange rates from external API");
            Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
            log.info("Successfully fetched exchange rates");
            return response;
        } catch (RestClientException e) {
            log.error("Error fetching exchange rates: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch exchange rates from external API", e);
        }
    }

    /**
     * Converts USD price to specified currency
     */
    public BigDecimal convertPrice(BigDecimal usdPrice, String targetCurrency) {
        try {
            Map<String, Object> response = getExchangeRates();
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");

            if (rates != null && rates.containsKey(targetCurrency)) {
                Double rate = rates.get(targetCurrency);
                BigDecimal convertedPrice = usdPrice.multiply(BigDecimal.valueOf(rate));
                return convertedPrice.setScale(2, RoundingMode.HALF_UP);
            }

            log.warn("Currency {} not found, returning original price", targetCurrency);
            return usdPrice;
        } catch (Exception e) {
            log.error("Error converting price: {}", e.getMessage());
            return usdPrice;
        }
    }

    /**
     * Gets available currencies from the API
     */
    public java.util.Set<String> getAvailableCurrencies() {
        try {
            Map<String, Object> response = getExchangeRates();
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            return rates != null ? rates.keySet() : java.util.Set.of();
        } catch (Exception e) {
            log.error("Error getting available currencies: {}", e.getMessage());
            return java.util.Set.of("USD", "EUR", "GBP", "TRY");
        }
    }
}
