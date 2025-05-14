package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    
}
