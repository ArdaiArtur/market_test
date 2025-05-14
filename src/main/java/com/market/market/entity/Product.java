package com.market.market.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id", length = 10, nullable = false)
    private String productId;

    @Column(name = "product_name", length = 200, nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // Foreign key to Unit entity
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "unit_id",
        nullable = false
    )
    private Unit unit;
    
    @Column(name = "package_qty", precision = 8, scale = 2, nullable = false)
    private BigDecimal packageQty;

    @OneToMany(mappedBy="product")
    private List<PriceSnapshot>snapshots;

    // JPA no-arg constructor
    public Product() { }

    // Convenience constructor
    public Product(String productId,
                   String productName,
                   Category category,
                   Brand brand, Unit unit,BigDecimal packageQty) {
        this.productId   = productId;
        this.productName = productName;
        this.category  = category;
        this.brand     = brand;
        this.unit = unit;
        this.packageQty = packageQty;
        
    }

    // Getter & setter for productId
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Getter & setter for productName
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter & setter for category
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    // Getter & setter for brand
    public Brand getBrand() {
        return brand;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

        public BigDecimal getPackageQty() {
        return packageQty;
    }

    public void setPackageQty(BigDecimal packageQty) {
        this.packageQty = packageQty;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<PriceSnapshot> getPriceSnapshots() {
    return snapshots;
    }

    public void setPriceSnapshots(List<PriceSnapshot> snapshots) {
        this.snapshots = snapshots;
    }

}
