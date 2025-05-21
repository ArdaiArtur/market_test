package com.market.market.service;

import com.market.market.dto.PriceHistoryDto;
import com.market.market.dto.WeeklyPriceHistoryDto;
import com.market.market.entity.PriceSnapshot;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.PromotionApplyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class PriceSnapshotService {

    private final PriceSnapshotRepository snapshotRepo;
    private final PromotionApplyRepository applyRepo;

    public PriceSnapshotService(PriceSnapshotRepository snapshotRepo,
            PromotionApplyRepository applyRepo) {
        this.snapshotRepo = snapshotRepo;
        this.applyRepo = applyRepo;
    }

    /**
     * Returns weekly price‐history DTOs for a product between [from,to],
     * optional filters by storeId, categoryId, brandId.
     */
    @Transactional(readOnly = true)
    public List<WeeklyPriceHistoryDto> getWeeklyPriceHistory(
            String productName,
            LocalDate from,
            LocalDate to,
            Integer storeId,
            Integer categoryId,
            Integer brandId) {
        // 1) Fetch raw snapshots for this product & date range
        List<PriceSnapshot> snaps = snapshotRepo
                .findByProduct_ProductNameAndSnapshotDateBetween(productName, from, to);

        // 2) Filter by store/category/brand
        snaps = snaps.stream()
                .filter(s -> storeId == null || storeId.equals(s.getMagazine().getMagazineId()))
                .filter(s -> categoryId == null || categoryId.equals(s.getProduct().getCategory().getCategoryId()))
                .filter(s -> brandId == null || brandId.equals(s.getProduct().getBrand().getBrandId()))
                .collect(Collectors.toList());

        // 3) Group by week‐start (Monday)
        Map<LocalDate, List<PriceSnapshot>> byWeek = snaps.stream()
                .collect(Collectors.groupingBy(s -> s.getSnapshotDate()
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))));

        return byWeek.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    LocalDate weekStart = entry.getKey();
                    List<PriceSnapshot> ws = entry.getValue();

                    // convert each snapshot in this week to a PriceHistoryDto
                    List<PriceHistoryDto> points = ws.stream().map(s -> {
                        BigDecimal orig = s.getPrice();
                        BigDecimal pct = applyRepo
                                .findActiveBySnapshotOn(s.getSnapshotId(), s.getSnapshotDate())
                                .stream()
                                .map(pa -> pa.getPromotion().getDiscountPct())
                                .findFirst()
                                .orElse(BigDecimal.ZERO);
                        BigDecimal amt = orig.multiply(pct)
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        BigDecimal curr = orig.subtract(amt);

                        return new PriceHistoryDto(
                                s.getProduct().getProductId(),
                                s.getProduct().getProductName(),
                                s.getProduct().getCategory().getName(),
                                s.getProduct().getBrand().getName(),
                                weekStart,
                                orig,
                                curr,
                                pct,
                                amt,
                                s.getMagazine().getName());
                    }).toList();

                    return new WeeklyPriceHistoryDto(weekStart, points);
                })
                .toList();
    }

}
