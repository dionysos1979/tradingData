package com.traderpatient.tradingdata.model;

import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class Currency extends BaseEntity {

	private String code;
	private String currency;

	public Currency() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Currency [code=" + code + ", currency=" + currency + "]";
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
}
