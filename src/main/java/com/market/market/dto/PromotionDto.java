package com.market.market.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PromotionDto(
    LocalDate promotionStart,
    LocalDate promotionEnd,
    BigDecimal percentage
) {}
