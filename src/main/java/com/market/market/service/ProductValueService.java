package com.market.market.service;

import com.market.market.dto.PricePerUnitDto;
import com.market.market.dto.ProductSnapshotDto;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Product;
import com.market.market.entity.PromotionApply;
import com.market.market.repository.ProductRepository;
import com.market.market.repository.PromotionApplyRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductValueService {

    private final ProductRepository productRepository;

    private final PromotionApplyRepository promotionApplyRepository;

    public ProductValueService(ProductRepository productRepository,PromotionApplyRepository promotionApplyRepository)
    {
        this.productRepository=productRepository;
        this.promotionApplyRepository=promotionApplyRepository;
    }
    /**
     * Get value per unit for products in a specific category
     * @param categoryId The category ID to filter products
     * @return List of products with their value per unit
     */
    public List<PricePerUnitDto> getProductsByCategory(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
        return calculateValuePerUnit(products);
    }

    /**
     * Get value per unit for products with similar names (for substitution suggestions)
     * @param productName The product name to search for
     * @return List of similar products with their value per unit
     */
    public List<PricePerUnitDto> getSimilarProducts(String productName) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);
        return calculateValuePerUnit(products);
    }

    /**
     * Calculate value per unit for a list of products
     * @param products List of products
     * @return List of DTOs with value per unit calculations
     */
    private List<PricePerUnitDto> calculateValuePerUnit(List<Product> products) {
        List<PricePerUnitDto> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        for (Product product : products) {
            // Get the latest price snapshot for the product
            List<PriceSnapshot> snapshots = product.getPriceSnapshots();
            if (snapshots == null || snapshots.isEmpty()) {
                continue; // Skip products without price information
            }

            // Get the most recent price snapshot
            List<PriceSnapshot> latestSnapshots = snapshots.stream()
                .filter(s -> s.getSnapshotDate().equals(monday))
                .collect(Collectors.toList());


            if (latestSnapshots == null) {
                continue; // Skip if no valid price snapshot
            }

            List<ProductSnapshotDto> details = new ArrayList<>();

            for (PriceSnapshot latestSnapshot : latestSnapshots) {
                    
                

                // Check if there's an active promotion
                List<PromotionApply> promotions = promotionApplyRepository.findActiveBySnapshotOn(latestSnapshot.getSnapshotId(),monday);
                boolean hasActivePromotion = false;
                BigDecimal originalPrice = null;
                
                if (promotions != null && !promotions.isEmpty()) {
                    hasActivePromotion =true;
                }

                if (hasActivePromotion) {
                    // Get the highest discount if multiple promotions
                    BigDecimal maxDiscountPct = promotions.stream()
                        .filter(p -> !p.getPromotion().getToDate().isBefore(monday) &&
                                !p.getPromotion().getFromDate().isAfter(monday))
                        .map(p -> p.getPromotion().getDiscountPct())
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);

                    // Calculate original price
                    BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                        maxDiscountPct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
                    originalPrice = latestSnapshot.getPrice().divide(
                        discountMultiplier, 2, RoundingMode.HALF_UP);
                }

                // Calculate value per unit (price per standard unit)
                // We need to:
                // 1. Convert the package quantity to the base unit using conversion factor
                // 2. Calculate price per base unit
                BigDecimal standardizedQty = product.getPackageQty().multiply(product.getUnit().getConversionFactor());
                BigDecimal valuePerUnit = latestSnapshot.getPrice().divide(standardizedQty, 4, RoundingMode.HALF_UP);

                ProductSnapshotDto dto = new ProductSnapshotDto(
                    originalPrice,
                    latestSnapshot.getPrice(),
                    valuePerUnit,
                    latestSnapshot.getMagazine().getName(), // assuming snapshot has getStore()
                    latestSnapshot.getCurrency()
                );
                details.add(dto);

            }
            // Create DTO using the record constructor
            PricePerUnitDto dto = new PricePerUnitDto(
                product.getProductId(),
                product.getProductName(),
                product.getBrand().getName(),
                product.getCategory().getName(),
                product.getPackageQty(),
                product.getUnit().getLabel(),
                details
            );

            result.add(dto);
        }

        // Sort by value per unit (ascending - lower value per unit is better)
        return result.stream()
            .sorted(Comparator.comparing(PricePerUnitDto::productId))
            .collect(Collectors.toList());
    }
    
    /**
     * Find substitute products with better value per unit
     * @param productId The original product ID
     * @return List of possible substitutes with better value per unit
     */
    public List<PricePerUnitDto> findSubstitutes(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return new ArrayList<>();
        }
        
        // Find products in the same category
        List<Product> categoryProducts = productRepository.findByCategoryCategoryId(
            product.getCategory().getCategoryId());
        
        // Remove the original product
        categoryProducts = categoryProducts.stream()
            .filter(p -> !p.getProductId().equals(productId))
            .collect(Collectors.toList());
            
        // Calculate value per unit for all products
        List<PricePerUnitDto> allProducts = calculateValuePerUnit(categoryProducts);
        
        // Get the value per unit of the original product
        List<PricePerUnitDto> originalProductValue = calculateValuePerUnit(List.of(product));
        if (originalProductValue.isEmpty()) {
            return allProducts;
        }
        
        
        
        // Filter products with better value per unit (lower price per unit)
        return allProducts.stream()
            .collect(Collectors.toList());
    }
}