package com.traderpatient.tradingdata.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Price implements Comparable<Price>{

	@Id
	@GeneratedValue
	private Integer id;
	@Temporal(TemporalType.DATE)
	private Date date;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double adjustedClose;
	private Double volume;
	private String ticker;

	public Price() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getAdjustedClose() {
		return adjustedClose;
	}
	public void setAdjustedClose(Double adjustedClose) {
		this.adjustedClose = adjustedClose;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(Price p) {
			return p.getDate().compareTo(this.getDate());
	}

	public boolean equals(Price p) {
		return this.getDate().equals(p.getDate())
				&& this.getTicker().equals(p.getTicker())
				&& this.getOpen().equals(p.getOpen())
				&& this.getHigh().equals(p.getHigh())
				&& this.getLow().equals(p.getLow())
				&& this.getClose().equals(p.getClose())
				&& this.getAdjustedClose().equals(p.getAdjustedClose()) ;
	}

}
