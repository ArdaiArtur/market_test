package com.market.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByProductName(String productName);

    List<Product> findByCategoryCategoryId(Integer categoryId);
    
    List<Product> findByProductNameContainingIgnoreCase(String productName);

}
