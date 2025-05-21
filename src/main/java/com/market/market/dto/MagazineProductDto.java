package com.market.market.dto;

import java.util.List;

public record MagazineProductDto(
    String magazineName,
    List<ProductInfoDto> products
) {}

