package com.market.market.dto;

import java.math.BigDecimal;

public record ProductDiscountDto(
    String productId,
    String productName,
    BigDecimal originalPrice,
    BigDecimal currentPrice,
    BigDecimal discountPercentage,
    BigDecimal discountNumber,
    String storeName
) {}
