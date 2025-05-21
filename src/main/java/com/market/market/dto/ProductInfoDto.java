package com.market.market.dto;

import java.math.BigDecimal;

public record ProductInfoDto(
    String productId,
    String name,
    Integer quantity,
    BigDecimal price,
    BigDecimal discountPercentage,
    BigDecimal newPrice
) {}

