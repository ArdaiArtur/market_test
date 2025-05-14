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
@Table(name = "category")
public class Category extends  BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    // No-arg constructor for JPA
    public Category() { }

    // Convenience constructor
    public Category(String name) {
        super(name);
    }

    // Getters & setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    @OneToMany(mappedBy="category")
    private List<Product>products;

    public List<Product> getProducts() {
    return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
