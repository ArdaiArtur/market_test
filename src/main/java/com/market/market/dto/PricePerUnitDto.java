package com.market.market.dto;

import java.math.BigDecimal;

public record PricePerUnitDto(
    String productId,
    String productName,
    String categoryName,
    BigDecimal price,
    BigDecimal packageQty,
    String unitCode,
    BigDecimal baseQty,          // e.g. 0.75 (liters or kilograms)
    BigDecimal pricePerBaseUnit  // e.g. €/kg or €/l
) {}

