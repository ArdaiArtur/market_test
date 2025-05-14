package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    
}
