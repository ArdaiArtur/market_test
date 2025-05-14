package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Magazine;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

    
}
