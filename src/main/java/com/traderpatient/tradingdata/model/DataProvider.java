package com.traderpatient.tradingdata.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class DataProvider {

	public static final String YAHOO = "YAHOO";
	public static final String FINVIZ = "FINVIZ";
	public static final String INVESTING = "INVESTING";
	public static final String ALPHA_VANTAGE = "ALPHA_VANTAGE";
	public static final String POLYGON = "POLYGON";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String url;

	@OneToMany
	private List<IndiceTicker> tickers;

	public DataProvider() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IndiceTicker> getTickers() {
		return tickers;
	}

	public void setTickers(List<IndiceTicker> tickers) {
		this.tickers = tickers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DataProvider that = (DataProvider) o;
		return Objects.equals(name, that.name) && Objects.equals(url, that.url) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, url, tickers);
	}
}
