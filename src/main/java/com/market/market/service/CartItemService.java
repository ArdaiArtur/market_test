package com.market.market.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.market.market.dto.MagazineProductDto;
import com.market.market.dto.ProductInfoDto;
import com.market.market.entity.CartItem;
import com.market.market.entity.Magazine;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Promotion;
import com.market.market.entity.PromotionApply;
import com.market.market.repository.CartItemRepository;
import com.market.market.repository.PriceSnapshotRepository;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepo;
    private final PriceSnapshotRepository snapshotRepo;

    public CartItemService(CartItemRepository cartItemRepo, PriceSnapshotRepository snapshotRepo) {
        this.cartItemRepo = cartItemRepo;
        this.snapshotRepo = snapshotRepo;
    }

    public List<MagazineProductDto> getGroupedCartItemsByMagazine(Integer cartId) {
        List<CartItem> items = cartItemRepo.findByCart_CartId(cartId);
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Map<String, List<ProductInfoDto>> grouped = new HashMap<>();

        for (CartItem item : items) {
            List<PriceSnapshot> snapshots = snapshotRepo
                .findByProduct_ProductIdAndSnapshotDate(item.getProduct().getProductId(), monday);

            for (PriceSnapshot snapshot : snapshots) {
                Magazine mag = snapshot.getMagazine();
                grouped.putIfAbsent(mag.getName(), new ArrayList<>());

                LocalDate snapshotDate = snapshot.getSnapshotDate();
                LocalDate snapshotDatePlus6 = snapshotDate.plusDays(6);

                Optional<PromotionApply> activePromotionApply = snapshot.getPromotionApplys().stream()
                .filter(pa -> {
                    Promotion p = pa.getPromotion();
                    return ( !p.getFromDate().isAfter(snapshotDate) )   // p.fromDate <= snapshotDate
                        && ( !p.getToDate().isBefore(snapshotDatePlus6) );  // p.toDate >= snapshotDate + 6 days
                })
                .findFirst();

                BigDecimal discountPercentage = activePromotionApply
                .map(pa -> pa.getPromotion().getDiscountPct())
                .orElse(BigDecimal.ZERO);

                BigDecimal newPrice = snapshot.getPrice().subtract(
                    snapshot.getPrice().multiply(discountPercentage)
                        .divide(BigDecimal.valueOf(100))
                );

                grouped.get(mag.getName()).add(new ProductInfoDto(
                    item.getProduct().getProductId(),
                    item.getProduct().getProductName(),
                    item.getQuantity(),
                    snapshot.getPrice(),
                    discountPercentage,
                    newPrice
                ));
            }
        }

        // Convert to DTO list
        return grouped.entrySet().stream()
            .map(e -> new MagazineProductDto(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }
}

