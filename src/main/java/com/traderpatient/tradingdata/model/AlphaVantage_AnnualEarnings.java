package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_AnnualEarnings {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String ticker;
	private Date date;
	private Date dateMaj;
	private Date fiscalDateEnding;
	private Double reportedEPS;

	public AlphaVantage_AnnualEarnings() {

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

	public Double getReportedEPS() {
		return reportedEPS;
	}

	public void setReportedEPS(Double reportedEPS) {
		this.reportedEPS = reportedEPS;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

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
		return "AlphaVantage_AnnualEarnings{" +
				"id=" + id +
				", date='" + date + '\'' +
				", dateMaj='" + dateMaj + '\'' +
				", ticker='" + ticker + '\'' +
				", fiscalDateEnding=" + fiscalDateEnding +
				", reportedEPS=" + reportedEPS +
				'}';
	}
}
