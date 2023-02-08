package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.traderpatient.tradingdata.model.balanceSheet.BalanceSheet;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_BalanceSheetApi {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String ticker;

	@Transient
	private List<BalanceSheet> annualReports = new ArrayList<BalanceSheet>();

	@Transient
	private List<BalanceSheet> quarterlyReports = new ArrayList<BalanceSheet>();

	public AlphaVantage_BalanceSheetApi() {

	}

	public List<BalanceSheet> getAnnualReports() {
		return annualReports;
	}

	public void setAnnualReports(List<BalanceSheet> annualReports) {
		this.annualReports = annualReports;
	}

	public List<BalanceSheet> getQuarterlyReports() {
		return quarterlyReports;
	}

	public void setQuarterlyReports(List<BalanceSheet> quarterlyReports) {
		this.quarterlyReports = quarterlyReports;
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

}
