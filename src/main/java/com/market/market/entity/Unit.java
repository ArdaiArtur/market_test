package com.market.market.entity;

import java.math.BigDecimal;
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

    @Column(name = "conversion_factor", precision = 10, scale = 4, nullable = false)
    private BigDecimal conversionFactor;  // e.g. 0.001 for g, 1 for kg

    // JPA requires a no-arg constructor
    public Unit() { }

    // Convenience constructor
    public Unit(String code, String label,BigDecimal conversionFactor) {
        this.code = code;
        this.label = label;
        this.conversionFactor=conversionFactor;
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

        // Getter & setter for conversionFactor
    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }
    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
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
