package com.market.market.service;

import com.market.market.dto.MagazineProductDto;
import com.market.market.dto.ProductInfoDto;
import com.market.market.entity.*;
import com.market.market.repository.CartItemRepository;
import com.market.market.repository.PriceSnapshotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemServiceTest {

    private CartItemRepository cartItemRepository;
    private PriceSnapshotRepository priceSnapshotRepository;
    private CartItemService cartItemService;

    @BeforeEach
    public void setup() {
        cartItemRepository = mock(CartItemRepository.class);
        priceSnapshotRepository = mock(PriceSnapshotRepository.class);
        cartItemService = new CartItemService(cartItemRepository, priceSnapshotRepository);
    }

    @Test
    public void testGetGroupedCartItemsByMagazine_withValidData_returnsExpectedResult() {
        // Arrange
        Integer cartId = 1;
        String productId = "P001";
        String productName = "Apple";
        int quantity = 2;
        BigDecimal originalPrice = new BigDecimal("10.00");
        BigDecimal discountPct = new BigDecimal("20.00");

        LocalDate snapshotDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate promotionStart = snapshotDate.minusDays(1);
        LocalDate promotionEnd = snapshotDate.plusDays(7);

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        Magazine magazine = new Magazine();
        magazine.setName("Weekly Deals");

        Promotion promotion = new Promotion();
        promotion.setDiscountPct(discountPct);
        promotion.setFromDate(promotionStart);
        promotion.setToDate(promotionEnd);

        PromotionApply promotionApply = new PromotionApply();
        promotionApply.setPromotion(promotion);

        PriceSnapshot snapshot = new PriceSnapshot();
        snapshot.setProduct(product);
        snapshot.setMagazine(magazine);
        snapshot.setSnapshotDate(snapshotDate);
        snapshot.setPrice(originalPrice);
        snapshot.setPromotionApplys(List.of(promotionApply));

        when(cartItemRepository.findByCart_CartId(cartId)).thenReturn(List.of(cartItem));
        when(priceSnapshotRepository.findByProduct_ProductIdAndSnapshotDate(productId, snapshotDate)).thenReturn(List.of(snapshot));

        // Act
        List<MagazineProductDto> result = cartItemService.getGroupedCartItemsByMagazine(cartId);

        // Assert
        assertEquals(1, result.size());
        MagazineProductDto dto = result.get(0);
        assertEquals("Weekly Deals", dto.magazineName());
        assertEquals(1, dto.products().size());

        ProductInfoDto productDto = dto.products().get(0);
        assertEquals(productId, productDto.productId());
        assertEquals(productName, productDto.name());
        assertEquals(quantity, productDto.quantity());
        assertEquals(originalPrice, productDto.price());
        assertEquals(discountPct, productDto.discountPercentage());

        BigDecimal expectedNewPrice = originalPrice.subtract(originalPrice.multiply(discountPct).divide(BigDecimal.valueOf(100)));
        assertEquals(0, expectedNewPrice.compareTo(productDto.newPrice()));
    }

    @Test
    public void testGetGroupedCartItemsByMagazine_withNoCartItems_returnsEmptyList() {
        when(cartItemRepository.findByCart_CartId(1)).thenReturn(Collections.emptyList());

        List<MagazineProductDto> result = cartItemService.getGroupedCartItemsByMagazine(1);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetGroupedCartItemsByMagazine_withNoSnapshots_returnsEmptyList() {
        Integer cartId = 1;
        Product product = new Product();
        product.setProductId("P001");
        product.setProductName("Banana");

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        when(cartItemRepository.findByCart_CartId(cartId)).thenReturn(List.of(cartItem));
        when(priceSnapshotRepository.findByProduct_ProductIdAndSnapshotDate(anyString(), any())).thenReturn(Collections.emptyList());

        List<MagazineProductDto> result = cartItemService.getGroupedCartItemsByMagazine(cartId);

        assertTrue(result.isEmpty());
    }
}
