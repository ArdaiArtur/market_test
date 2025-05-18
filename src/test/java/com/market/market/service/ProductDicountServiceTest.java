package com.market.market.service;

import com.market.market.dto.ProductDiscountDto;
import com.market.market.entity.Magazine;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Product;
import com.market.market.entity.Promotion;
import com.market.market.entity.PromotionApply;
import com.market.market.repository.PromotionApplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductDiscountServiceTest {

    private PromotionApplyRepository applyRepository;
    private ProductDiscountService productDiscountService;

    @BeforeEach
    void setUp() {
        applyRepository = mock(PromotionApplyRepository.class);
        productDiscountService = new ProductDiscountService(applyRepository);
    }

    @Test
    void getBestDiscounts_returnsSortedDiscountsByDiscountValue() {
        // Arrange: mock data
       Product product1 = new Product();
    product1.setProductId("P001");
    product1.setProductName("Test Product 1");

    Product product2 = new Product();
    product2.setProductId("P002");
    product2.setProductName("Test Product 2");

    // Create two magazines (stores)
    Magazine magazine1 = new Magazine();
    magazine1.setName("Test Store 1");

    Magazine magazine2 = new Magazine();
    magazine2.setName("Test Store 2");

    // Create promotions with different discounts
    Promotion promo1 = new Promotion();
    promo1.setDiscountPct(new BigDecimal("10"));

    Promotion promo2 = new Promotion();
    promo2.setDiscountPct(new BigDecimal("20"));

    // Create separate price snapshots
    PriceSnapshot snapshot1 = new PriceSnapshot();
    snapshot1.setProduct(product1);
    snapshot1.setMagazine(magazine1);
    snapshot1.setPrice(new BigDecimal("100"));

    PriceSnapshot snapshot2 = new PriceSnapshot();
    snapshot2.setProduct(product2);
    snapshot2.setMagazine(magazine2);
    snapshot2.setPrice(new BigDecimal("100"));

    // Create PromotionApply instances with different promos and snapshots
    PromotionApply apply1 = new PromotionApply();
    apply1.setPromotion(promo1);
    apply1.setPriceSnapshot(snapshot1);

    PromotionApply apply2 = new PromotionApply();
    apply2.setPromotion(promo2);
    apply2.setPriceSnapshot(snapshot2);

    // Mock the repository to return both promotions
    when(applyRepository.findActiveOn(any(LocalDate.class)))
            .thenReturn(List.of(apply1, apply2));

    // Act
    List<ProductDiscountDto> result = productDiscountService.getBestDiscounts(LocalDate.of(2025, 5, 1));

    // Assert: check there are two results sorted by discount descending
    assertThat(result).hasSize(2);

    // The first one should be the 20% discount (apply2)
    assertThat(result.get(0).productName()).isEqualTo("Test Product 2");
    assertThat(result.get(0).discountPercentage()).isEqualTo(new BigDecimal("20"));
    assertThat(result.get(0).originalPrice()).isEqualTo(new BigDecimal("100"));
    assertThat(result.get(0).currentPrice()).isEqualTo(new BigDecimal("80.00"));
    assertThat(result.get(0).storeName()).isEqualTo("Test Store 2");

    // The second one should be the 10% discount (apply1)
    assertThat(result.get(1).productName()).isEqualTo("Test Product 1");
    assertThat(result.get(1).discountPercentage()).isEqualTo(new BigDecimal("10"));
    assertThat(result.get(1).originalPrice()).isEqualTo(new BigDecimal("100"));
    assertThat(result.get(1).currentPrice()).isEqualTo(new BigDecimal("90.00"));
    assertThat(result.get(1).storeName()).isEqualTo("Test Store 1");

    // Verify repository called once
    verify(applyRepository, times(1)).findActiveOn(any(LocalDate.class));
    }
}
