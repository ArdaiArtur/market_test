package com.market.market.dto;

import java.math.BigDecimal;

public record ProductSnapshotDto(
    BigDecimal originalPrice,
    BigDecimal currentPrice,
    BigDecimal valuePerUnit,
    String magazineName,
    String currency
) {}

