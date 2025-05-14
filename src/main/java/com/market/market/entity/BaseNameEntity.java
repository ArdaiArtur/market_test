package com.market.market.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract  class BaseNameEntity implements Serializable{
    
    protected BaseNameEntity() { }

    protected BaseNameEntity(String name) {
        this.name = name;
    }

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

}
