package com.market.market.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.PromotionApply;

@Repository
public interface PromotionApplyRepository extends JpaRepository<PromotionApply, Integer> {


}
