package com.traderpatient.tradingdata.model;


import com.traderpatient.tradingdata.model.base.BaseStockEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Indice extends BaseStockEntity {

    private String ticker;

    @Transient
    private List<Polygon_Quote> historicalPrices = new ArrayList<>();

    public Indice() {
        super();
    }

    public Indice(String name, String ticker) {
        // TODO Auto-generated constructor stub
        super(name);
        this.setTicker(ticker);
    }

    public List<Polygon_Quote> getHistoricalPrices() {
        return historicalPrices;
    }

    public void setHistoricalPrices(List<Polygon_Quote> historicalPrices) {
        this.historicalPrices = historicalPrices;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "Indice{" +
                "super=" + super.toString() +
                "ticker=" + ticker +
                '}';
    }
}

