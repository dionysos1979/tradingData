package com.traderpatient.tradingdata.model;


import com.traderpatient.tradingdata.model.base.BaseStockEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@MappedSuperclass
public class Stock extends BaseStockEntity {

	private String ticker;
	
	@ManyToOne
	private MarketPlace marketPlace;
	
	@OneToOne
	private Earning earning;
	
	@ManyToOne
	private Industry industry;
	
	@ManyToOne
	private Country country;

	@OneToMany
	private List<Polygon_Quote> historicalPrices = new ArrayList<>();
	
	public String getTicker() {
		return ticker;
	}


	public void setTicker(String ticker) {
		this.ticker = ticker;
	}


	public Industry getIndustry() {
		return industry;
	}


	public void setIndustry(Industry industry) {
		this.industry = industry;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Stock [ticker=" + ticker + ", name=" + super.getName() + ", industry=" + industry + ", country=" + country + "]";
	}

	public MarketPlace getMarketPlace() {
		return marketPlace;
	}


	public void setMarketPlace(MarketPlace marketPlace) {
		this.marketPlace = marketPlace;
	}

	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public Earning getEarning() {
		return earning;
	}

	public void setEarning(Earning earning) {
		this.earning = earning;
	}

	public List<Polygon_Quote> getHistoricalPrices() {
		return historicalPrices;
	}

	public void setHistoricalPrices(List<Polygon_Quote> historicalPrices) {
		this.historicalPrices = historicalPrices;
	}
}
