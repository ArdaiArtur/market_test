package com.market.market.controller;

import com.market.market.dto.ProductDto;
import com.market.market.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection is preferred
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET  /api/products
     * 
     * @return List of all products as ProductDto
     */
    @GetMapping
    public List<ProductDto> listAllProducts() {
        return productService.getAllProducts();
    }
}
