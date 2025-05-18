package com.market.market.controller;

import com.market.market.dto.PriceHistoryDto;
import com.market.market.dto.ProductDiscountDto;
import com.market.market.dto.WeeklyPriceHistoryDto;
import com.market.market.service.PriceSnapshotService;
import com.market.market.service.ProductDiscountService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prices/snapshots")
public class PriceSnapshotController {

    private final PriceSnapshotService priceSnapshotService;

    public PriceSnapshotController(PriceSnapshotService priceSnapshotService) {
        this.priceSnapshotService = priceSnapshotService;
    }

    @GetMapping("/products/{productName}/price-history")
    public List<WeeklyPriceHistoryDto> history(
            @PathVariable String productName,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to,
            @RequestParam(required = false) Integer store,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer brand) {
        return priceSnapshotService.getWeeklyPriceHistory(productName, from, to, store, category, brand);
    }
}
