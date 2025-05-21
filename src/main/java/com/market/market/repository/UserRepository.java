package com.market.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.market.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    
}
