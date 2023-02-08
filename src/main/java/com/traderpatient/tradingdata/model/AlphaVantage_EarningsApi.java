package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_EarningsApi {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonProperty(value= "symbol")
	private String ticker;

	@Transient
	private List<AlphaVantage_AnnualEarnings> annualEarnings = new ArrayList<AlphaVantage_AnnualEarnings>();

	@Transient
	private List<AlphaVantage_QuarterlyEarnings> quarterlyEarnings = new ArrayList<AlphaVantage_QuarterlyEarnings>();

	private Date date;

	public AlphaVantage_EarningsApi() {
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

	public List<AlphaVantage_AnnualEarnings> getAnnualEarnings() {
		return annualEarnings;
	}

	public void setAnnualEarnings(List<AlphaVantage_AnnualEarnings> annualEarnings) {
		this.annualEarnings = annualEarnings;
	}

	public List<AlphaVantage_QuarterlyEarnings> getQuarterlyEarnings() {
		return quarterlyEarnings;
	}

	public void setQuarterlyEarnings(List<AlphaVantage_QuarterlyEarnings> quarterlyEarnings) {
		this.quarterlyEarnings = quarterlyEarnings;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "AlphaVantage_EarningsApi{" +
				"id=" + id +
				", ticker='" + ticker + '\'' +
				", annualEarnings=" + annualEarnings +
				", quarterlyEarnings=" + quarterlyEarnings +
				", date=" + date +
				'}';
	}
}
