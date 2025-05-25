package com.market.market.service;

import com.market.market.dto.PriceHistoryDto;
import com.market.market.dto.WeeklyPriceHistoryDto;
import com.market.market.entity.*;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.PromotionApplyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PriceSnapshotServiceTest {

    private PriceSnapshotRepository snapshotRepo;
    private PromotionApplyRepository applyRepo;
    private PriceSnapshotService service;

    @BeforeEach
    void setUp() {
        snapshotRepo = mock(PriceSnapshotRepository.class);
        applyRepo = mock(PromotionApplyRepository.class);
        service = new PriceSnapshotService(snapshotRepo, applyRepo);
    }

    @Test
    void testGetWeeklyPriceHistory_WithOneSnapshotAndPromotion() {
        // Setup dates
        LocalDate date = LocalDate.of(2024, 5, 20); // a Monday
        LocalDate from = date.minusDays(1);
        LocalDate to = date.plusDays(1);

        // Setup entities
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Fruits");

        Brand brand = new Brand();
        brand.setBrandId(2);
        brand.setName("FreshCo");

        Unit unit = new Unit("kg", "Kilogram", BigDecimal.ONE);
        unit.setUnitId(1);

        Product product = new Product("apple", "Apple", category, brand, unit, BigDecimal.ONE);

        Magazine magazine = new Magazine();
        magazine.setMagazineId(100);
        magazine.setName("Main Store");

        PriceSnapshot snapshot = new PriceSnapshot();
        snapshot.setSnapshotId(1);
        snapshot.setSnapshotDate(date);
        snapshot.setPrice(new BigDecimal("10.00"));
        snapshot.setProduct(product);
        snapshot.setMagazine(magazine);

        Promotion promotion = new Promotion();
        promotion.setDiscountPct(new BigDecimal("20")); // 20%

        PromotionApply promoApply = new PromotionApply();
        promoApply.setPromotion(promotion);

        // Mocking
        when(snapshotRepo.findByProduct_ProductNameAndSnapshotDateBetween("Apple", from, to))
            .thenReturn(List.of(snapshot));

        when(applyRepo.findActiveBySnapshotOn(1, date))
            .thenReturn(List.of(promoApply));

        // Call the method
        List<WeeklyPriceHistoryDto> result = service.getWeeklyPriceHistory("Apple", from, to, 100, 1, 2);

        // Assert
        assertEquals(1, result.size());
        WeeklyPriceHistoryDto weekly = result.get(0);
        assertEquals(date, weekly.weekStart());

        PriceHistoryDto dto = weekly.points().get(0);
        assertEquals("Apple", dto.productName());
        assertEquals(new BigDecimal("10.00"), dto.originalPrice());
        assertEquals(new BigDecimal("2.00"), dto.discountAmount());
        assertEquals(new BigDecimal("8.00"), dto.currentPrice());
        assertEquals(new BigDecimal("20"), dto.discountPercentage());
        assertEquals("Main Store", dto.storeName());

    }

    @Test
    void testGetWeeklyPriceHistory_NoResults() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 7);

        when(snapshotRepo.findByProduct_ProductNameAndSnapshotDateBetween("NonExistent", from, to))
            .thenReturn(emptyList());

        List<WeeklyPriceHistoryDto> result = service.getWeeklyPriceHistory("NonExistent", from, to, null, null, null);
        assertTrue(result.isEmpty());
    }
}
