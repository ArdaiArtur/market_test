package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    
}
