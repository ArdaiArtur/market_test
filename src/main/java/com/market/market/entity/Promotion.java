package com.market.market.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id", nullable = false)
    private Integer promotionId;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "discount_pct", precision = 5, scale = 2, nullable = false)
    private BigDecimal discountPct;

    // JPA no-arg constructor
    public Promotion() {}

    // Convenience constructor
    public Promotion(LocalDate fromDate, LocalDate toDate, BigDecimal discountPct) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.discountPct = discountPct;
    }

    // Getters and setters
    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getDiscountPct() {
        return discountPct;
    }

    public void setDiscountPct(BigDecimal discountPct) {
        this.discountPct = discountPct;
    }

    @OneToMany(mappedBy="promotion")
    private List<PromotionApply>applys;

    public List<PromotionApply> getPromotionApplys() {
    return applys;
    }

    public void setPromotionApplys(List<PromotionApply> applys) {
        this.applys = applys;
    }
}
