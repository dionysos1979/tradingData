package com.traderpatient.tradingdata.model.base;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BaseStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseStockEntity() {
    }
    public BaseStockEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseStockEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
