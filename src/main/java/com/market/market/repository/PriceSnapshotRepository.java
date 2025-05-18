package com.market.market.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.PriceSnapshot;

@Repository
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Integer> {

    Optional<PriceSnapshot> findTopByOrderBySnapshotDateDesc();

    Optional<BigDecimal> findFirstByProductProductIdOrderBySnapshotDateDesc(String productId);

    List<PriceSnapshot> findByProduct_ProductIdAndSnapshotDateBetween(
            String productId, LocalDate from, LocalDate to);

    List<PriceSnapshot> findByProduct_ProductNameAndSnapshotDateBetween(
            String productName, LocalDate from, LocalDate to);
}
