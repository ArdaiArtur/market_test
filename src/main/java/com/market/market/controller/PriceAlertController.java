package com.market.market.controller;

import com.market.market.dto.PriceAlertCreateDto;
import com.market.market.dto.PriceAlertResponseDto;
import com.market.market.service.PriceAlertService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-alerts")
public class PriceAlertController {

    private final PriceAlertService priceAlertService;

    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    // POST /api/price-alerts
    // Create a new price alert
    @PostMapping
    public ResponseEntity<PriceAlertResponseDto> createAlert(@RequestBody PriceAlertCreateDto dto) {
        try {
            PriceAlertResponseDto createdAlert = priceAlertService.createAlert(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
        } catch (IllegalArgumentException e) {
            // Could be improved by a global exception handler
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/price-alerts/active
    // Retrieve all active, non-triggered alerts
    @GetMapping("/active")
    public List<PriceAlertResponseDto> getActiveAlerts() {
        return priceAlertService.getActiveAlerts();
    }

    // Optional: POST /api/price-alerts/check
    // Manually trigger checking alerts (useful for testing)
    @PostMapping("/check")
    public ResponseEntity<Void> checkPriceAlerts() {
        priceAlertService.checkPriceAlerts();
        return ResponseEntity.ok().build();
    }
}
