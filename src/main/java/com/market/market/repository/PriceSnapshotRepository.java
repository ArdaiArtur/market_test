package com.market.market.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Magazine;
import com.market.market.entity.PriceSnapshot;
import com.market.market.entity.Product;

@Repository
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Integer> {

    Optional<PriceSnapshot> findTopByOrderBySnapshotDateDesc();

    Optional<BigDecimal> findFirstByProductProductIdOrderBySnapshotDateDesc(String productId);

    List<PriceSnapshot> findByProduct_ProductIdAndSnapshotDateBetween(
            String productId, LocalDate from, LocalDate to);

    List<PriceSnapshot> findByProduct_ProductNameAndSnapshotDateBetween(
            String productName, LocalDate from, LocalDate to);

    @Query(value = "SELECT ps.price FROM price_snapshot ps WHERE ps.product_id = :productId ORDER BY ps.snapshot_date DESC LIMIT 1", nativeQuery = true)
    Optional<BigDecimal> findLatestPriceByProductId(@Param("productId") String productId);

    boolean existsByMagazineAndProductAndSnapshotDate(
        Magazine magazine,
        Product product,
        LocalDate snapshotDate
    );

    List<PriceSnapshot> findBySnapshotDate(LocalDate snapshotDate);

    Optional<PriceSnapshot> findTopByProduct_ProductIdAndSnapshotDateOrderBySnapshotDateDesc(String productId, LocalDate snapshotDate);

    List<PriceSnapshot> findByProduct_ProductIdAndSnapshotDate(String productId, LocalDate snapshotDate);       
}
