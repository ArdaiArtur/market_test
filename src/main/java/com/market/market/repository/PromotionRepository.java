package com.market.market.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

     boolean existsByFromDateAndToDateAndDiscountPct(LocalDate fromDate, LocalDate toDate, BigDecimal discountPct);

        @Query("""
      SELECT p
        FROM Promotion p
       WHERE p.fromDate <= :date
         AND p.toDate   >= :date
      """)
    List<Promotion> findActivePromotions(@Param("date") LocalDate date);
}
