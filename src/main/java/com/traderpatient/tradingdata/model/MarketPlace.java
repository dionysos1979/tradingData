package com.traderpatient.tradingdata.model;


import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class MarketPlace extends BaseEntity {
	private String code;
	private String marketPlace;

	public MarketPlace() {
		super();
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMarketPlace() {
		return marketPlace;
	}

	public void setMarketPlace(String marketPlace) {
		this.marketPlace = marketPlace;
	}


	
}
