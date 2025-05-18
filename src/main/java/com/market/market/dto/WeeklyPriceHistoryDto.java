package com.market.market.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents one calendar-week’s worth of price-history points.
 */
public record WeeklyPriceHistoryDto(
    LocalDate weekStart,             // e.g. Monday of that week
    List<PriceHistoryDto> points     // all the snapshots (or averages) in that week
) {}