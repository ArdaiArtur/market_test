package com.market.market.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PriceHistoryDto(
    String productId,
    String productName,
    String categoryName,
    String brandName,
    LocalDate date,
    BigDecimal originalPrice,
    BigDecimal currentPrice,
    BigDecimal discountPercentage,
    BigDecimal discountAmount,
    String storeName
) {}
