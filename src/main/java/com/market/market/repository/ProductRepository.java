package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    
}
