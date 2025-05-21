package com.market.market.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PriceAlertResponseDto(
    Integer alertId,
    Integer userId,
    String productId,
    BigDecimal targetPrice,
    boolean triggered,
    boolean active,
    LocalDate createdAt,
    LocalDateTime triggeredAt
) {}
