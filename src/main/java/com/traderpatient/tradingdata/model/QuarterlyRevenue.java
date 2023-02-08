package com.traderpatient.tradingdata.model;

import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.Objects;

@Entity
public class QuarterlyRevenue extends BaseEntity {

    private String ticker;

    public QuarterlyRevenue() {
        super();
        this.dateMaj = new Date();
    }

    public Date date;
    public Date dateMaj;

    public Double revenue;

    public QuarterlyRevenue(String name, String ticker) {
        this.setTicker(ticker);
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuarterlyRevenue that = (QuarterlyRevenue) o;
        return Objects.equals(ticker, that.ticker) && Objects.equals(date, that.date)
                && Objects.equals(revenue, that.revenue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, date, revenue);
    }

    @Override
    public String toString() {
        return "QuarterlyRevenue{" +
                "ticker='" + ticker + '\'' +
                ", date=" + date +
                ", dateMaj=" + dateMaj +
                ", revenue=" + revenue +
                '}';
    }
}

