package com.market.market.service;

import com.market.market.dto.ProductDiscountDto;
import com.market.market.entity.Magazine;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.PromotionApply;
import com.market.market.entity.Product;
import com.market.market.repository.PromotionApplyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductDiscountService {

    private final PromotionApplyRepository applyRepository;

    public ProductDiscountService(PromotionApplyRepository applyRepository) {
        this.applyRepository = applyRepository;
    }

    /**
     * Lists all products currently under promotion (today), sorted by promo discountPct desc.
     */
    public List<ProductDiscountDto> getBestDiscounts(LocalDate day) {

        if (day == null) {
            day = LocalDate.now();
        }

        return applyRepository.findActiveOn(day).stream()
            .map(this::mapApplyToDto)
            .sorted(Comparator.comparing(ProductDiscountDto::discountPercentage).reversed())
            .toList();
    }

    private ProductDiscountDto mapApplyToDto(PromotionApply apply) {
        PriceSnapshot snapshot = apply.getPriceSnapshot();
        Product product       = snapshot.getProduct();
        Magazine magazine       = snapshot.getMagazine();
        // Use the promotion’s stored discountPct directly:
        BigDecimal price = snapshot.getPrice();
        var discountPct = apply.getPromotion().getDiscountPct();
        BigDecimal discountAmount = price
        .multiply(discountPct)
        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal discountedPrice = price.subtract(discountAmount);

        return new ProductDiscountDto(
            product.getProductId(),
            product.getProductName(),
            snapshot.getPrice(),
            discountedPrice,
            discountPct,
            discountAmount,
            magazine.getName()
        );
    }
}
