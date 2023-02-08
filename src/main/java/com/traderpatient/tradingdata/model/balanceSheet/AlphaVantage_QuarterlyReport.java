package com.traderpatient.tradingdata.model.balanceSheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Date;

@Entity
@JsonIgnoreProperties
public class AlphaVantage_QuarterlyReport {
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String ticker;
	private Date date;
	private Date dateMaj;
	private Date fiscalDateEnding;
	private String reportedCurrency;
	private String grossProfit;
	private String totalRevenue;
	private String costOfRevenue;
	private String costofGoodsAndServicesSold;
	private String operatingIncome;
	private String sellingGeneralAndAdministrative;
	private String researchAndDevelopment;
	private String operatingExpenses;
	private String investmentIncomeNet;
	private String netInterestIncome;
	private String interestIncome;
	private String interestExpense;
	private String nonInterestIncome;
	private String otherNonOperatingIncome;
	private String depreciation;
	private String depreciationAndAmortization;
	private String incomeBeforeTax;
	private String incomeTaxExpense;
	private String interestAndDebtExpense;
	private String netIncomeFromContinuingOperations;
	private String comprehensiveIncomeNetOfTax;
	private String ebit;
	private String ebitda;
	private String netIncome;

	public AlphaVantage_QuarterlyReport() {

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

	public Date getFiscalDateEnding() {
		return fiscalDateEnding;
	}

	public void setFiscalDateEnding(Date fiscalDateEnding) {
		this.fiscalDateEnding = fiscalDateEnding;
	}

	public String getReportedCurrency() {
		return reportedCurrency;
	}

	public void setReportedCurrency(String reportedCurrency) {
		this.reportedCurrency = reportedCurrency;
	}

	public String getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(String grossProfit) {
		this.grossProfit = grossProfit;
	}

	public String getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(String totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getCostOfRevenue() {
		return costOfRevenue;
	}

	public void setCostOfRevenue(String costOfRevenue) {
		this.costOfRevenue = costOfRevenue;
	}

	public String getCostofGoodsAndServicesSold() {
		return costofGoodsAndServicesSold;
	}

	public void setCostofGoodsAndServicesSold(String costofGoodsAndServicesSold) {
		this.costofGoodsAndServicesSold = costofGoodsAndServicesSold;
	}

	public String getOperatingIncome() {
		return operatingIncome;
	}

	public void setOperatingIncome(String operatingIncome) {
		this.operatingIncome = operatingIncome;
	}

	public String getSellingGeneralAndAdministrative() {
		return sellingGeneralAndAdministrative;
	}

	public void setSellingGeneralAndAdministrative(String sellingGeneralAndAdministrative) {
		this.sellingGeneralAndAdministrative = sellingGeneralAndAdministrative;
	}

	public String getResearchAndDevelopment() {
		return researchAndDevelopment;
	}

	public void setResearchAndDevelopment(String researchAndDevelopment) {
		this.researchAndDevelopment = researchAndDevelopment;
	}

	public String getOperatingExpenses() {
		return operatingExpenses;
	}

	public void setOperatingExpenses(String operatingExpenses) {
		this.operatingExpenses = operatingExpenses;
	}

	public String getInvestmentIncomeNet() {
		return investmentIncomeNet;
	}

	public void setInvestmentIncomeNet(String investmentIncomeNet) {
		this.investmentIncomeNet = investmentIncomeNet;
	}

	public String getNetInterestIncome() {
		return netInterestIncome;
	}

	public void setNetInterestIncome(String netInterestIncome) {
		this.netInterestIncome = netInterestIncome;
	}

	public String getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(String interestIncome) {
		this.interestIncome = interestIncome;
	}

	public String getInterestExpense() {
		return interestExpense;
	}

	public void setInterestExpense(String interestExpense) {
		this.interestExpense = interestExpense;
	}

	public String getNonInterestIncome() {
		return nonInterestIncome;
	}

	public void setNonInterestIncome(String nonInterestIncome) {
		this.nonInterestIncome = nonInterestIncome;
	}

	public String getOtherNonOperatingIncome() {
		return otherNonOperatingIncome;
	}

	public void setOtherNonOperatingIncome(String otherNonOperatingIncome) {
		this.otherNonOperatingIncome = otherNonOperatingIncome;
	}

	public String getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(String depreciation) {
		this.depreciation = depreciation;
	}

	public String getDepreciationAndAmortization() {
		return depreciationAndAmortization;
	}

	public void setDepreciationAndAmortization(String depreciationAndAmortization) {
		this.depreciationAndAmortization = depreciationAndAmortization;
	}

	public String getIncomeBeforeTax() {
		return incomeBeforeTax;
	}

	public void setIncomeBeforeTax(String incomeBeforeTax) {
		this.incomeBeforeTax = incomeBeforeTax;
	}

	public String getIncomeTaxExpense() {
		return incomeTaxExpense;
	}

	public void setIncomeTaxExpense(String incomeTaxExpense) {
		this.incomeTaxExpense = incomeTaxExpense;
	}

	public String getInterestAndDebtExpense() {
		return interestAndDebtExpense;
	}

	public void setInterestAndDebtExpense(String interestAndDebtExpense) {
		this.interestAndDebtExpense = interestAndDebtExpense;
	}

	public String getNetIncomeFromContinuingOperations() {
		return netIncomeFromContinuingOperations;
	}

	public void setNetIncomeFromContinuingOperations(String netIncomeFromContinuingOperations) {
		this.netIncomeFromContinuingOperations = netIncomeFromContinuingOperations;
	}

	public String getComprehensiveIncomeNetOfTax() {
		return comprehensiveIncomeNetOfTax;
	}

	public void setComprehensiveIncomeNetOfTax(String comprehensiveIncomeNetOfTax) {
		this.comprehensiveIncomeNetOfTax = comprehensiveIncomeNetOfTax;
	}

	public String getEbit() {
		return ebit;
	}

	public void setEbit(String ebit) {
		this.ebit = ebit;
	}

	public String getEbitda() {
		return ebitda;
	}

	public void setEbitda(String ebitda) {
		this.ebitda = ebitda;
	}

	public String getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
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
		return "AlphaVantage_QuarterlyReport{" +
				"id=" + id +
				", ticker='" + ticker + '\'' +
				", date=" + date +
				", dateMaj=" + dateMaj +
				", fiscalDateEnding=" + fiscalDateEnding +
				", reportedCurrency='" + reportedCurrency + '\'' +
				", grossProfit='" + grossProfit + '\'' +
				", totalRevenue='" + totalRevenue + '\'' +
				", costOfRevenue='" + costOfRevenue + '\'' +
				", costofGoodsAndServicesSold='" + costofGoodsAndServicesSold + '\'' +
				", operatingIncome='" + operatingIncome + '\'' +
				", sellingGeneralAndAdministrative='" + sellingGeneralAndAdministrative + '\'' +
				", researchAndDevelopment='" + researchAndDevelopment + '\'' +
				", operatingExpenses='" + operatingExpenses + '\'' +
				", investmentIncomeNet='" + investmentIncomeNet + '\'' +
				", netInterestIncome='" + netInterestIncome + '\'' +
				", interestIncome='" + interestIncome + '\'' +
				", interestExpense='" + interestExpense + '\'' +
				", nonInterestIncome='" + nonInterestIncome + '\'' +
				", otherNonOperatingIncome='" + otherNonOperatingIncome + '\'' +
				", depreciation='" + depreciation + '\'' +
				", depreciationAndAmortization='" + depreciationAndAmortization + '\'' +
				", incomeBeforeTax='" + incomeBeforeTax + '\'' +
				", incomeTaxExpense='" + incomeTaxExpense + '\'' +
				", interestAndDebtExpense='" + interestAndDebtExpense + '\'' +
				", netIncomeFromContinuingOperations='" + netIncomeFromContinuingOperations + '\'' +
				", comprehensiveIncomeNetOfTax='" + comprehensiveIncomeNetOfTax + '\'' +
				", ebit='" + ebit + '\'' +
				", ebitda='" + ebitda + '\'' +
				", netIncome='" + netIncome + '\'' +
				'}';
	}

	public static Comparator<AlphaVantage_QuarterlyReport> SORT_BY_FISCAL_DATE = new Comparator<AlphaVantage_QuarterlyReport>() {
		public int compare(AlphaVantage_QuarterlyReport one, AlphaVantage_QuarterlyReport other) {
			return other.fiscalDateEnding.compareTo(one.fiscalDateEnding);
		}
	};
}
