package com.market.market.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "price_alerts",
       indexes = {
         @Index(name = "idx_alert_user",    columnList = "user_id"),
         @Index(name = "idx_alert_product", columnList = "product_id"),
         @Index(name = "idx_alert_active",  columnList = "active")
       }
)
public class PriceAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Integer alertId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_alert_user"))
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_alert_product"))
    private Product product;

    @Column(name = "target_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal targetPrice;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "triggered", nullable = false)
    private boolean triggered = false;

    @Column(name = "created_at", nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdAt;

    @Column(name = "triggered_at")
    private LocalDateTime triggeredAt;

    public PriceAlert() {}

    public PriceAlert(User user, Product product, BigDecimal targetPrice) {
        this.user = user;
        this.product = product;
        this.targetPrice = targetPrice;
    }

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }
    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isTriggered() {
        return triggered;
    }
    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getTriggeredAt() {
        return triggeredAt;
    }
    public void setTriggeredAt(LocalDateTime triggeredAt) {
        this.triggeredAt = triggeredAt;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
    }

}
