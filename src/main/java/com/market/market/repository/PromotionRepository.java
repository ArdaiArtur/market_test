package com.market.market.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

     boolean existsByFromDateAndToDateAndDiscountPct(LocalDate fromDate, LocalDate toDate, BigDecimal discountPct);
}
