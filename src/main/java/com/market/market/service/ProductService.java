package com.market.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.market.market.dto.ProductDto;
import com.market.market.entity.Product;
import com.market.market.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
            .map(this::mapToDto)
            .toList();
    }

    private ProductDto mapToDto(Product product) {
        return new ProductDto(
            product.getProductId(),
            product.getProductName(),
            product.getCategory() != null
                ? product.getCategory().getName()
                : null,
            product.getBrand() != null
                ? product.getBrand().getName()
                : null,
            product.getUnit() != null
                ? product.getUnit().getCode()
                : null,
            product.getPackageQty()
        );
    }
}

