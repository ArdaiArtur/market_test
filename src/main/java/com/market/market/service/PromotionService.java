package com.market.market.service;

import com.market.market.dto.PromotionDto;
import com.market.market.entity.Promotion;
import com.market.market.entity.PromotionApply;
import com.market.market.repository.PromotionApplyRepository;
import com.market.market.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    private final PromotionApplyRepository promotionApplyRepository;

    public PromotionService(PromotionRepository promotionRepository ,PromotionApplyRepository promotionApplyRepository) {
        this.promotionRepository = promotionRepository;
        this.promotionApplyRepository=promotionApplyRepository;
    }

    /**
     * Returns promotions whose fromDate equals today's date.
     * @return list of promotions starting today
     */
    public List<PromotionDto> getPromotionsStartingToday() {
        LocalDate today = LocalDate.now();

        return promotionRepository.findPromotionsStartingToday(LocalDate.of(2025, 5, 1)).stream()
        .map(this::mapToPromotionDto).toList();
    }

        public List<PromotionDto> getActivPromotionsStartingToday() {
        LocalDate today = LocalDate.now();
        
        return promotionApplyRepository.findActivePromotion(LocalDate.of(2025, 5, 1)).stream()
        .map(this::mapToDto).toList();
    }

    private PromotionDto mapToDto(PromotionApply apply)
    {
        Promotion promotion =apply.getPromotion();
        return new PromotionDto(promotion.getFromDate(),promotion.getToDate(),promotion.getDiscountPct());
    }

        private PromotionDto mapToPromotionDto(Promotion promotion)
    {
        
        return new PromotionDto(promotion.getFromDate(),promotion.getToDate(),promotion.getDiscountPct());
    }

    /**
     * Returns promotions whose fromDate equals the given date.
     * @param date the date to filter promotions by
     * @return list of promotions starting on the given date
     */
    public List<Promotion> getPromotionsByDate(LocalDate date) {
        LocalDate filterDate = (date != null) ? date : LocalDate.now();
        return promotionRepository.findPromotionsStartingToday(filterDate);
    }
}
