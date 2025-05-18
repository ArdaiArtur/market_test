package com.market.market.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.market.dto.PromotionDto;
import com.market.market.service.PromotionService;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/new")
    public List<PromotionDto> newPromotions() {
        return promotionService.getPromotionsStartingToday();
    }

        @GetMapping("/activ")
    public List<PromotionDto> getActivPromotionsStartingToday() {
        return promotionService.getActivPromotionsStartingToday();
    }
}

