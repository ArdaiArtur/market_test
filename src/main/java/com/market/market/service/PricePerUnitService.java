package com.market.market.service;

import com.market.market.dto.PricePerUnitDto;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Product;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.ProductRepository;
import com.market.market.repository.PromotionApplyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class PricePerUnitService {

    private final ProductRepository productRepo;
    private final PriceSnapshotRepository snapshotRepo;
    private final PromotionApplyRepository applyRepo;

    public PricePerUnitService(ProductRepository productRepo,
            PriceSnapshotRepository snapshotRepo,PromotionApplyRepository applyRepo) {
        this.productRepo = productRepo;
        this.snapshotRepo = snapshotRepo;
        this.applyRepo    = applyRepo;
    }

    /**
     * Finds the top‐N best value products (price per base unit) in the given
     * category.
     *
     * @param categoryId the category to search within
     * @param limit      max number of results to return
     *//* 
    @Transactional(readOnly = true)
    public PricePerUnitDto findBestValueInProducts(String productName) {
        return productRepo.findByProductName(productName).stream()
            .map(this::mapToDto)                // compute DTO (or null if not mappable)
            .filter(Objects::nonNull)
            .min(Comparator.comparing(PricePerUnitDto::pricePerBaseUnit))
            .orElse(null);
    }
*/
    /**
     i will need a new dto price per unit dto will be the main one and the other one will be
     the datat abaut the markets cuz im selectign a product type like milk
     i have 3 milk prodcuts whit difernt brand /qt
     for each of them i will get the price of them based of the markets so 
     dto{
     datat,
     marketbaeddto{
     data of the price / unit of the product for the specific store }
     }
     adn i will retun 3 of the firsst dto cuz i got 3 type of milk so i get back the best price for each store and best price / quantity
     well i could leave it all nd like calculate the price / quantity based of the lowesst price of a store of the respective item
     
     *//* 
    private PricePerUnitDto mapToDto(Product product) {
        // 1) latest snapshot (by date descending)
        PriceSnapshot snap = snapshotRepo
            .findFirstByProduct_ProductIdOrderBySnapshotDateDesc(product.getProductId())
            .orElse(null);
        if (snap == null) return null;

        // 2) check for an active discount on that snapshot date
        BigDecimal discountPct = applyRepo
            .findActiveBySnapshotOn(snap.getSnapshotId(), snap.getSnapshotDate())
            .stream()
            .map(pa -> pa.getPromotion().getDiscountPct())
            .findFirst()
            .orElse(BigDecimal.ZERO);

        BigDecimal originalPrice  = snap.getPrice();
        BigDecimal discountAmount = originalPrice
            .multiply(discountPct)
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal discountedPrice = originalPrice.subtract(discountAmount);

        // 3) normalize to base unit (kg or l)
        BigDecimal factor = product.getUnit().getConversionFactor();
        if (factor == null || factor.compareTo(BigDecimal.ZERO) == 0) return null;

        BigDecimal baseQty = product.getPackageQty().multiply(factor);
        if (baseQty.compareTo(BigDecimal.ZERO) <= 0) return null;

        // 4) compute €/base unit
        BigDecimal ppu = discountedPrice
            .divide(baseQty, 4, RoundingMode.HALF_UP);

        // 5) assemble DTO
        return new PricePerUnitDto(
            product.getProductId(),
            product.getProductName(),
            product.getCategory().getCategoryName(),
            originalPrice,
            discountedPrice,
            product.getPackageQty(),
            product.getUnit().getCode(),
            baseQty,
            discountPct,
            discountAmount,
            ppu,
            snap.getMagazine().getName()
        );
    }
        */
}
