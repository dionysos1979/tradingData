package com.traderpatient.tradingdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Set;

@Entity
public class IndiceTicker {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String ticker;
	private String url;
	private Integer dataProviderId;
	private Integer indiceId;

	public IndiceTicker() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDataProviderId() {
		return dataProviderId;
	}

	public void setDataProviderId(Integer dataProviderId) {
		this.dataProviderId = dataProviderId;
	}

	public Integer getIndiceId() {
		return indiceId;
	}

	public void setIndiceId(Integer indiceId) {
		this.indiceId = indiceId;
	}
}
