package com.market.market.dto;

import java.math.BigDecimal;
import java.util.List;

public record PricePerUnitDto(
    String productId,
     String productName,
     String brandName,
     String categoryName,
     BigDecimal packageQty,
     String unitLabel,
     List<ProductSnapshotDto> snapshots
) {}

