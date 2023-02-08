package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.*;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_EarningsCalendar {
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonProperty(value= "symbol")
	private String ticker;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date reportDate;
	@Temporal(TemporalType.DATE)
	private Date fiscalDateEnding;
	private String estimate;
	private String currency;
	@Temporal(TemporalType.DATE)
	private Date date;

	public AlphaVantage_EarningsCalendar() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getFiscalDateEnding() {
		return fiscalDateEnding;
	}

	public void setFiscalDateEnding(Date fiscalDateEnding) {
		this.fiscalDateEnding = fiscalDateEnding;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AlphaVantage_EarningsCalendar that = (AlphaVantage_EarningsCalendar) o;
		return Objects.equals(ticker, that.ticker)
				&& Objects.equals(fiscalDateEnding, that.fiscalDateEnding)
				&& Objects.equals(reportDate, that.reportDate)
				&& Objects.equals(estimate, that.estimate)
				&& Objects.equals(currency, that.currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, ticker, name, reportDate, fiscalDateEnding, estimate, currency, date);
	}

	@Override
	public String toString() {
		return "AlphaVantage_EarningsCalendar{" +
				"id=" + id +
				", ticker='" + ticker + '\'' +
				", name='" + name + '\'' +
				", reportDate=" + reportDate +
				", fiscalDateEnding=" + fiscalDateEnding +
				", estimate='" + estimate + '\'' +
				", currency='" + currency + '\'' +
				", date=" + date +
				'}';
	}

	public static Comparator<AlphaVantage_EarningsCalendar> SORT_BY_FISCAL_DATE = new Comparator<AlphaVantage_EarningsCalendar>() {
		public int compare(AlphaVantage_EarningsCalendar one, AlphaVantage_EarningsCalendar other) {
			return other.fiscalDateEnding.compareTo(one.fiscalDateEnding);
		}
	};

	public static Comparator<AlphaVantage_EarningsCalendar> SORT_BY_FISCAL_DATE_REVERSE = new Comparator<AlphaVantage_EarningsCalendar>() {
		public int compare(AlphaVantage_EarningsCalendar one, AlphaVantage_EarningsCalendar other) {
			return one.fiscalDateEnding.compareTo(other.fiscalDateEnding);
		}
	};
}
