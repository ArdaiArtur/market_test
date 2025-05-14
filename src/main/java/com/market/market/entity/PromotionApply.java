package com.market.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotion_apply")
public class PromotionApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id", nullable = false)
    private Integer applyId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_snapshot_id", nullable = false)
    private PriceSnapshot priceSnapshot;

    // Constructors
    public PromotionApply() {}

    public PromotionApply(Promotion promotion, PriceSnapshot priceSnapshot) {
        this.promotion = promotion;
        this.priceSnapshot = priceSnapshot;
    }

    // Getters and setters
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public PriceSnapshot getPriceSnapshot() {
        return priceSnapshot;
    }

    public void setPriceSnapshot(PriceSnapshot priceSnapshot) {
        this.priceSnapshot = priceSnapshot;
    }
}
