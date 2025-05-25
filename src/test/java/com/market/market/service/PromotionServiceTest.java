package com.market.market.service;

import com.market.market.dto.PromotionDto;
import com.market.market.entity.Promotion;
import com.market.market.repository.PromotionApplyRepository;
import com.market.market.repository.PromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PromotionServiceTest {

    private PromotionRepository promotionRepository;
    private PromotionApplyRepository promotionApplyRepository;
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        promotionRepository = mock(PromotionRepository.class);
        promotionApplyRepository = mock(PromotionApplyRepository.class);
        promotionService = new PromotionService(promotionRepository, promotionApplyRepository);
    }

    @Test
    void getPromotionsStartingToday_returnsPromotionsStartingOnMonday() {
        // Arrange: create test promotions
        Promotion promo1 = new Promotion();
        promo1.setFromDate(LocalDate.of(2025, 5, 5)); // Monday
        promo1.setToDate(LocalDate.of(2025, 5, 11));
        promo1.setDiscountPct(new BigDecimal("15"));

        Promotion promo2 = new Promotion();
        promo2.setFromDate(LocalDate.of(2025, 5, 5)); // Same Monday
        promo2.setToDate(LocalDate.of(2025, 5, 12));
        promo2.setDiscountPct(new BigDecimal("25"));

        // Calculate expected Monday date
        LocalDate today = LocalDate.now();
        LocalDate expectedMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Mock repository to return test promotions
        when(promotionRepository.findPromotionsStartingToday(expectedMonday))
                .thenReturn(List.of(promo1, promo2));

        // Act
        List<PromotionDto> result = promotionService.getPromotionsStartingToday();

        // Assert
        assertThat(result).hasSize(2);

        assertThat(result.get(0).promotionStart()).isEqualTo(LocalDate.of(2025, 5, 5));
        assertThat(result.get(0).promotionEnd()).isEqualTo(LocalDate.of(2025, 5, 11));
        assertThat(result.get(0).percentage()).isEqualTo(new BigDecimal("15"));

        // Check second promotion
        assertThat(result.get(1).promotionStart()).isEqualTo(LocalDate.of(2025, 5, 5));
        assertThat(result.get(1).promotionEnd()).isEqualTo(LocalDate.of(2025, 5, 12));
        assertThat(result.get(1).percentage()).isEqualTo(new BigDecimal("25"));


        // Verify repository was called with correct date
        verify(promotionRepository, times(1)).findPromotionsStartingToday(expectedMonday);
    }

    @Test
    void getPromotionsStartingToday_returnsEmptyListWhenNoPromotions() {
        // Arrange
        LocalDate today = LocalDate.now();
        LocalDate expectedMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        when(promotionRepository.findPromotionsStartingToday(expectedMonday))
                .thenReturn(List.of());

        // Act
        List<PromotionDto> result = promotionService.getPromotionsStartingToday();

        // Assert
        assertThat(result).isEmpty();
        verify(promotionRepository, times(1)).findPromotionsStartingToday(expectedMonday);
    }

    @Test
    void getActivPromotionsStartingToday_returnsActivePromotions() {
        // Arrange: create test promotions
        Promotion activePromo1 = new Promotion();
        activePromo1.setFromDate(LocalDate.of(2025, 5, 5));
        activePromo1.setToDate(LocalDate.of(2025, 5, 15));
        activePromo1.setDiscountPct(new BigDecimal("20"));

        Promotion activePromo2 = new Promotion();
        activePromo2.setFromDate(LocalDate.of(2025, 5, 5));
        activePromo2.setToDate(LocalDate.of(2025, 5, 20));
        activePromo2.setDiscountPct(new BigDecimal("30"));

        // Calculate expected Monday date
        LocalDate today = LocalDate.now();
        LocalDate expectedMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Mock repository to return active promotions
        when(promotionApplyRepository.findActivePromotion(expectedMonday))
                .thenReturn(List.of(activePromo1, activePromo2));

        // Act
        List<PromotionDto> result = promotionService.getActivPromotionsStartingToday();

        // Assert
        assertThat(result).hasSize(2);

        // Check first active promotion
        assertThat(result.get(0).promotionStart()).isEqualTo(LocalDate.of(2025, 5, 5));
        assertThat(result.get(0).promotionEnd()).isEqualTo(LocalDate.of(2025, 5, 15));
        assertThat(result.get(0).percentage()).isEqualTo(new BigDecimal("20"));

        // Check second active promotion
        assertThat(result.get(1).promotionStart()).isEqualTo(LocalDate.of(2025, 5, 5));
        assertThat(result.get(1).promotionEnd()).isEqualTo(LocalDate.of(2025, 5, 20));
        assertThat(result.get(1).percentage()).isEqualTo(new BigDecimal("30"));


        // Verify repository was called with correct date
        verify(promotionApplyRepository, times(1)).findActivePromotion(expectedMonday);
    }

    @Test
    void getActivPromotionsStartingToday_returnsEmptyListWhenNoActivePromotions() {
        // Arrange
        LocalDate today = LocalDate.now();
        LocalDate expectedMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        when(promotionApplyRepository.findActivePromotion(expectedMonday))
                .thenReturn(List.of());

        // Act
        List<PromotionDto> result = promotionService.getActivPromotionsStartingToday();

        // Assert
        assertThat(result).isEmpty();
        verify(promotionApplyRepository, times(1)).findActivePromotion(expectedMonday);
    }

    @Test
    void getPromotionsByDate_withValidDate_returnsPromotionsForThatDate() {
        // Arrange
        LocalDate testDate = LocalDate.of(2025, 6, 15);
        
        Promotion promo1 = new Promotion();
        promo1.setFromDate(testDate);
        promo1.setToDate(testDate.plusDays(7));
        promo1.setDiscountPct(new BigDecimal("10"));

        Promotion promo2 = new Promotion();
        promo2.setFromDate(testDate);
        promo2.setToDate(testDate.plusDays(14));
        promo2.setDiscountPct(new BigDecimal("15"));

        when(promotionRepository.findPromotionsStartingToday(testDate))
                .thenReturn(List.of(promo1, promo2));

        // Act
        List<Promotion> result = promotionService.getPromotionsByDate(testDate);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFromDate()).isEqualTo(testDate);
        assertThat(result.get(0).getDiscountPct()).isEqualTo(new BigDecimal("10"));
        assertThat(result.get(1).getFromDate()).isEqualTo(testDate);
        assertThat(result.get(1).getDiscountPct()).isEqualTo(new BigDecimal("15"));

        verify(promotionRepository, times(1)).findPromotionsStartingToday(testDate);
    }

    @Test
    void getPromotionsByDate_withNullDate_usesTodayAsDefault() {
        // Arrange
        LocalDate today = LocalDate.now();
        
        Promotion promo = new Promotion();
        promo.setFromDate(today);
        promo.setToDate(today.plusDays(5));
        promo.setDiscountPct(new BigDecimal("12"));

        when(promotionRepository.findPromotionsStartingToday(today))
                .thenReturn(List.of(promo));

        // Act
        List<Promotion> result = promotionService.getPromotionsByDate(null);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFromDate()).isEqualTo(today);
        assertThat(result.get(0).getDiscountPct()).isEqualTo(new BigDecimal("12"));

        verify(promotionRepository, times(1)).findPromotionsStartingToday(today);
    }

    @Test
    void getPromotionsByDate_withEmptyResult_returnsEmptyList() {
        // Arrange
        LocalDate testDate = LocalDate.of(2025, 12, 25);
        
        when(promotionRepository.findPromotionsStartingToday(testDate))
                .thenReturn(List.of());

        // Act
        List<Promotion> result = promotionService.getPromotionsByDate(testDate);

        // Assert
        assertThat(result).isEmpty();
        verify(promotionRepository, times(1)).findPromotionsStartingToday(testDate);
    }
}