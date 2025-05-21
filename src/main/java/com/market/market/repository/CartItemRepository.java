package com.market.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.market.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart_CartId(Integer cartId);
}
