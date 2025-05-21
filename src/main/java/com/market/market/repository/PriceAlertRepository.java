package com.market.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.PriceAlert;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Integer> {

     List<PriceAlert> findByTriggeredFalseAndActiveTrue();
}
