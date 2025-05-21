package com.market.market.controller;

import com.market.market.dto.PricePerUnitDto;
import com.market.market.service.ProductValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductValueController {

    @Autowired
    private ProductValueService productValueService;

    /**
     * Get products with value per unit in a specific category
     * @param categoryId The category ID to filter products
     * @return List of products with value per unit information
     */
    @GetMapping("/category/{categoryId}/value")
    public ResponseEntity<List<PricePerUnitDto>> getProductsValueByCategory(@PathVariable Integer categoryId) {
        List<PricePerUnitDto> products = productValueService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    /**
     * Get similar products with value per unit
     * @param productName The product name to search for
     * @return List of similar products with value per unit information
     */
    @GetMapping("/similar")
    public ResponseEntity<List<PricePerUnitDto>> getSimilarProducts(@RequestParam String productName) {
        List<PricePerUnitDto> products = productValueService.getSimilarProducts(productName);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Find substitutes with better value per unit
     * @param productId The original product ID
     * @return List of substitutes with better value per unit
     */
    @GetMapping("/{productId}/substitutes")
    public ResponseEntity<List<PricePerUnitDto>> findSubstitutes(@PathVariable String productId) {
        List<PricePerUnitDto> substitutes = productValueService.findSubstitutes(productId);
        return ResponseEntity.ok(substitutes);
    }
}