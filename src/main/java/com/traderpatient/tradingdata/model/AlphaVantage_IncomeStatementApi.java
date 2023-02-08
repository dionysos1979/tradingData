package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_AnnualReport;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_IncomeStatementApi {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String ticker;

	@Transient
	private List<AlphaVantage_AnnualReport> annualReports = new ArrayList<AlphaVantage_AnnualReport>();

	@Transient
	private List<AlphaVantage_QuarterlyReport> quarterlyReports = new ArrayList<AlphaVantage_QuarterlyReport>();

	public AlphaVantage_IncomeStatementApi() {

	}

	public List<AlphaVantage_AnnualReport> getAnnualReports() {
		return annualReports;
	}

	public void setAnnualReports(List<AlphaVantage_AnnualReport> annualReports) {
		this.annualReports = annualReports;
	}

	public List<AlphaVantage_QuarterlyReport> getQuarterlyReports() {
		return quarterlyReports;
	}

	public void setQuarterlyReports(List<AlphaVantage_QuarterlyReport> quarterlyReports) {
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
