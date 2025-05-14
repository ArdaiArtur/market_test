package com.market.market.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "price_snapshot")
public class PriceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snapshot_id", nullable = false)
    private Integer snapshotId;

    // Foreign key to Magazine entity
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "magazine_id", 
        nullable = false
    )
    private Magazine magazine;

    // Foreign key to Product entity
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "product_id",
        nullable = false
    )
    private Product product;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    // Constructors

    public PriceSnapshot() { }

    public PriceSnapshot(Magazine magazine, Product product, LocalDate snapshotDate,
                        BigDecimal price, String currency) {
        this.magazine = magazine;
        this.product = product;
        this.snapshotDate = snapshotDate;
        this.price = price;
        this.currency = currency;
    }

    // Getters & Setters

    public Integer getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Integer snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @OneToMany(mappedBy="priceSnapshot")
    private List<PromotionApply>applys;

    public List<PromotionApply> getPromotionApplys() {
    return applys;
    }

    public void setPromotionApplys(List<PromotionApply> applys) {
        this.applys = applys;
    }
}
