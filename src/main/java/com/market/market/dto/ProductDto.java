package com.market.market.dto;

import java.math.BigDecimal;

public record ProductDto(
    String productId,
    String productName,
    String categoryName,
    String brandName,
    String unitName,
    BigDecimal packageQty
) {}
