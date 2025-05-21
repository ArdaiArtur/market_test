package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.market.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
