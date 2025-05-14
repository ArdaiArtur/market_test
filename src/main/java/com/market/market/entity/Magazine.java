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
@Table(name = "magazine")
public class Magazine extends  BaseNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "magazine_id", nullable = false)
    private Integer magazineId;

    // No-arg constructor for JPA
    public Magazine() { }

    // Convenience constructor
    public Magazine(String name) {
        super(name);
    }

    // Getters & setters
    public Integer getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Integer magazineId) {
        this.magazineId = magazineId;
    }

        @OneToMany(mappedBy="magazine")
    private List<PriceSnapshot>snapshots;

    public List<PriceSnapshot> getPriceSnapshots() {
    return snapshots;
    }

    public void setPriceSnapshots(List<PriceSnapshot> snapshots) {
        this.snapshots = snapshots;
    }

}
