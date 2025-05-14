package com.market.market.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.PriceSnapshot;

@Repository
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Integer> {

    Optional<PriceSnapshot> findTopByOrderBySnapshotDateDesc();
    Optional<BigDecimal> findFirstByProductProductIdOrderBySnapshotDateDesc(String productId);
}
