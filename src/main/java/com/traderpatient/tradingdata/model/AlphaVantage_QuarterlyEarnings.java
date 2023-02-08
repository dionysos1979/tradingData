package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.util.Comparator;
import java.util.Date;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_QuarterlyEarnings {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String ticker;
	private Date date;
	private Date dateMaj;
	private Date fiscalDateEnding;
	private Date reportedDate;
	private String reportedEPS;
	private String estimatedEPS;
	private String surprise;
	private String surprisePercentage;

	public AlphaVantage_QuarterlyEarnings() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFiscalDateEnding() {
		return fiscalDateEnding;
	}

	public void setFiscalDateEnding(Date fiscalDateEnding) {
		this.fiscalDateEnding = fiscalDateEnding;
	}

	public Date getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}

	public String getReportedEPS() {
		return reportedEPS;
	}

	public void setReportedEPS(String reportedEPS) {
		this.reportedEPS = reportedEPS;
	}

	public String getEstimatedEPS() {
		return estimatedEPS;
	}

	public void setEstimatedEPS(String estimatedEPS) {
		this.estimatedEPS = estimatedEPS;
	}

	public String getSurprise() {
		return surprise;
	}

	public void setSurprise(String surprise) {
		this.surprise = surprise;
	}

	public String getSurprisePercentage() {
		return surprisePercentage;
	}

	public void setSurprisePercentage(String surprisePercentage) {
		this.surprisePercentage = surprisePercentage;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public static Comparator<AlphaVantage_QuarterlyEarnings> SORT_BY_REPORTED_DATE = new Comparator<AlphaVantage_QuarterlyEarnings>() {
		public int compare(AlphaVantage_QuarterlyEarnings one, AlphaVantage_QuarterlyEarnings other) {
			return other.reportedDate.compareTo(one.reportedDate);
		}
	};

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
	public String toString() {
		return "AlphaVantage_QuarterlyEarnings{" +
				"id=" + id +
				", date=" + date +
				", dateMaj=" + dateMaj +
				", ticker=" + ticker +
				", fiscalDateEnding=" + fiscalDateEnding +
				", reportedDate=" + reportedDate +
				", reportedEPS='" + reportedEPS + '\'' +
				", estimatedEPS='" + estimatedEPS + '\'' +
				", surprise='" + surprise + '\'' +
				", surprisePercentage='" + surprisePercentage + '\'' +
				'}';
	}
}
