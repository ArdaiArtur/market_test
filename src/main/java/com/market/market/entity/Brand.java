package com.market.market.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand extends  BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    // No-arg constructor for JPA
    public Brand() { }

    // Convenience constructor
    public Brand(String name) {
        super(name);
    }

    // Getters & setters
    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @OneToMany(mappedBy="brand")
    private List<Product>products;

    public List<Product> getProducts() {
    return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
