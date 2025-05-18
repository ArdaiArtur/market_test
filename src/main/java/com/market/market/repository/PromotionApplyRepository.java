package com.market.market.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.market.market.entity.PromotionApply;

@Repository
public interface PromotionApplyRepository extends JpaRepository<PromotionApply, Integer> {


    @Query("""
      SELECT pa
        FROM PromotionApply pa
       WHERE pa.promotion.fromDate <= :date
         AND pa.promotion.toDate   >= :date
      """)
    List<PromotionApply> findActiveOn(@Param("date") LocalDate date);

        @Query("""
      SELECT pa
        FROM PromotionApply pa
       WHERE pa.promotion.fromDate = :date
      """)
    List<PromotionApply> findActivePromotion(@Param("date") LocalDate date);
}
