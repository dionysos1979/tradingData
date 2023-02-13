package com.traderpatient.tradingdata.model.freeCashFLow;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Entity
public class FreeCashFlow {
	public static final String ANNUAL = "ANNUAL";
	public static final String QUARTERLY = "QUARTERLY";
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String ticker;
	private Date date;
	private Date dateMaj;
	private String frequence;
	private String dataProvider; // Source de la dernière mise à jour

	// Format AlphaVantage function=INCOME_STATEMENTS
	private String fiscalDateEnding ;
	private String reportedCurrency ;

	private String operatingCashflow ;
	private String freeCashFlow;

	public FreeCashFlow() {

	}

	public FreeCashFlow(Object object) {
	}

	public static Comparator<FreeCashFlow> SORT_BY_FISCAL_DATE = new Comparator<FreeCashFlow>() {
		public int compare(FreeCashFlow one, FreeCashFlow other) {
			return other.fiscalDateEnding.compareTo(one.fiscalDateEnding);
		}
	};

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

	public String getFrequence() {
		return frequence;
	}

	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}

	public String getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		this.dataProvider = dataProvider;
	}

	public String getFiscalDateEnding() {
		return fiscalDateEnding;
	}

	public void setFiscalDateEnding(String fiscalDateEnding) {
		this.fiscalDateEnding = fiscalDateEnding;
	}

	public String getReportedCurrency() {
		return reportedCurrency;
	}

	public void setReportedCurrency(String reportedCurrency) {
		this.reportedCurrency = reportedCurrency;
	}

	public String getOperatingCashflow() {
		return operatingCashflow;
	}

	public void setOperatingCashflow(String operatingCashflow) {
		this.operatingCashflow = operatingCashflow;
	}

	public String getFreeCashFlow() {
		return freeCashFlow;
	}

	public void setFreeCashFlow(String freeCashFlow) {
		this.freeCashFlow = freeCashFlow;
	}
}
