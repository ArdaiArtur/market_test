package com.market.market.dto;

import java.math.BigDecimal;

public record PriceAlertCreateDto(
    Integer userId,
    String productId,
    BigDecimal targetPrice
) {}
