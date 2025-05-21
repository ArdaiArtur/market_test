package com.market.market.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends  BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // No-arg constructor for JPA
    public User() { }

    // Convenience constructor
    public User(String name) {
        super(name);
    }

    // Getters & setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @OneToMany(mappedBy="user")
    private List<PriceAlert>priceAlerts;

    public List<PriceAlert> getPriceAlert() {
    return priceAlerts;
    }

    public void setPriceAlerts(List<PriceAlert> priceAlerts) {
        this.priceAlerts = priceAlerts;
    }

    @OneToOne(mappedBy="user")
    private Cart cart;

    public Cart getCart() {
    return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
