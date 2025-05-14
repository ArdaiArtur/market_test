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
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id", nullable = false)
    private Integer unitId;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "label", length = 50, nullable = false)
    private String label;

    // JPA requires a no-arg constructor
    public Unit() { }

    // Convenience constructor
    public Unit(String code, String label) {
        this.code = code;
        this.label = label;
    }

    // Getter & setter for unitId
    public Integer getUnitId() {
        return unitId;
    }
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    // Getter & setter for code
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    // Getter & setter for label
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

            @OneToMany(mappedBy="unit")
    private List<Product>products;

    public List<Product> getProduct() {
    return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }

}
