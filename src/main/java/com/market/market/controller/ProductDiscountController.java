package com.market.market.controller;

import com.market.market.dto.ProductDiscountDto;
import com.market.market.service.ProductDiscountService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products/discounts")
public class ProductDiscountController {

    private final ProductDiscountService productDiscountService;

    public ProductDiscountController(ProductDiscountService productDiscountService) {
        this.productDiscountService = productDiscountService;
    }

    /**
     * GET /api/products/best-discounts?limit=5
     *
     * @param limit maximum number of results to return (defaults to 5)
     * @return a list of products with the highest current percentage discounts
     */
    @GetMapping("/best-discounts")
   public List<ProductDiscountDto> getBestDiscounts(
        @RequestParam(value = "date", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date) {
        return productDiscountService.getBestDiscounts(date);
    }
}
