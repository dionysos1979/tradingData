package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonIgnoreProperties
public class FMP_AnnualIncomeStatement {
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date dateMaj;
	private String date;
	@JsonProperty(value= "symbol")
	private String ticker ;
	private String reportedCurrency ;
	private String cik ;
	private String fillingDate ;
	private String acceptedDate ;
	private String calendarYear ;
	private String period ;
	private String revenue ;
	private String costOfRevenue ;
	private String grossProfit ;
	private String grossProfitRatio ;
	private String researchAndDevelopmentExpenses ;
	private String generalAndAdministrativeExpenses ;
	private String sellingAndMarketingExpenses ;
	private String sellingGeneralAndAdministrativeExpenses ;
	private String otherExpenses ;
	private String operatingExpenses ;
	private String costAndExpenses ;
	private String interestIncome ;
	private String interestExpense ;
	private String depreciationAndAmortization ;
	private String ebitda ;
	private String ebitdaratio ;
	private String operatingIncome ;
	private String operatingIncomeRatio ;
	private String totalOtherIncomeExpensesNet ;
	private String incomeBeforeTax ;
	private String incomeBeforeTaxRatio ;
	private String incomeTaxExpense ;
	private String netIncome ;
	private String netIncomeRatio ;
	private String eps ;
	private String epsdiluted ;
	private String weightedAverageShsOut ;
	private String weightedAverageShsOutDil ;
	private String link ;
	private String finalLink ;

	public FMP_AnnualIncomeStatement() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getReportedCurrency() {
		return reportedCurrency;
	}

	public void setReportedCurrency(String reportedCurrency) {
		this.reportedCurrency = reportedCurrency;
	}

	public String getCik() {
		return cik;
	}

	public void setCik(String cik) {
		this.cik = cik;
	}

	public String getFillingDate() {
		return fillingDate;
	}

	public void setFillingDate(String fillingDate) {
		this.fillingDate = fillingDate;
	}

	public String getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(String acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getCalendarYear() {
		return calendarYear;
	}

	public void setCalendarYear(String calendarYear) {
		this.calendarYear = calendarYear;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getCostOfRevenue() {
		return costOfRevenue;
	}

	public void setCostOfRevenue(String costOfRevenue) {
		this.costOfRevenue = costOfRevenue;
	}

	public String getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(String grossProfit) {
		this.grossProfit = grossProfit;
	}

	public String getGrossProfitRatio() {
		return grossProfitRatio;
	}

	public void setGrossProfitRatio(String grossProfitRatio) {
		this.grossProfitRatio = grossProfitRatio;
	}

	public String getResearchAndDevelopmentExpenses() {
		return researchAndDevelopmentExpenses;
	}

	public void setResearchAndDevelopmentExpenses(String researchAndDevelopmentExpenses) {
		this.researchAndDevelopmentExpenses = researchAndDevelopmentExpenses;
	}

	public String getGeneralAndAdministrativeExpenses() {
		return generalAndAdministrativeExpenses;
	}

	public void setGeneralAndAdministrativeExpenses(String generalAndAdministrativeExpenses) {
		this.generalAndAdministrativeExpenses = generalAndAdministrativeExpenses;
	}

	public String getSellingAndMarketingExpenses() {
		return sellingAndMarketingExpenses;
	}

	public void setSellingAndMarketingExpenses(String sellingAndMarketingExpenses) {
		this.sellingAndMarketingExpenses = sellingAndMarketingExpenses;
	}

	public String getSellingGeneralAndAdministrativeExpenses() {
		return sellingGeneralAndAdministrativeExpenses;
	}

	public void setSellingGeneralAndAdministrativeExpenses(String sellingGeneralAndAdministrativeExpenses) {
		this.sellingGeneralAndAdministrativeExpenses = sellingGeneralAndAdministrativeExpenses;
	}

	public String getOtherExpenses() {
		return otherExpenses;
	}

	public void setOtherExpenses(String otherExpenses) {
		this.otherExpenses = otherExpenses;
	}

	public String getOperatingExpenses() {
		return operatingExpenses;
	}

	public void setOperatingExpenses(String operatingExpenses) {
		this.operatingExpenses = operatingExpenses;
	}

	public String getCostAndExpenses() {
		return costAndExpenses;
	}

	public void setCostAndExpenses(String costAndExpenses) {
		this.costAndExpenses = costAndExpenses;
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

	public String getDepreciationAndAmortization() {
		return depreciationAndAmortization;
	}

	public void setDepreciationAndAmortization(String depreciationAndAmortization) {
		this.depreciationAndAmortization = depreciationAndAmortization;
	}

	public String getEbitda() {
		return ebitda;
	}

	public void setEbitda(String ebitda) {
		this.ebitda = ebitda;
	}

	public String getEbitdaratio() {
		return ebitdaratio;
	}

	public void setEbitdaratio(String ebitdaratio) {
		this.ebitdaratio = ebitdaratio;
	}

	public String getOperatingIncome() {
		return operatingIncome;
	}

	public void setOperatingIncome(String operatingIncome) {
		this.operatingIncome = operatingIncome;
	}

	public String getOperatingIncomeRatio() {
		return operatingIncomeRatio;
	}

	public void setOperatingIncomeRatio(String operatingIncomeRatio) {
		this.operatingIncomeRatio = operatingIncomeRatio;
	}

	public String getTotalOtherIncomeExpensesNet() {
		return totalOtherIncomeExpensesNet;
	}

	public void setTotalOtherIncomeExpensesNet(String totalOtherIncomeExpensesNet) {
		this.totalOtherIncomeExpensesNet = totalOtherIncomeExpensesNet;
	}

	public String getIncomeBeforeTax() {
		return incomeBeforeTax;
	}

	public void setIncomeBeforeTax(String incomeBeforeTax) {
		this.incomeBeforeTax = incomeBeforeTax;
	}

	public String getIncomeBeforeTaxRatio() {
		return incomeBeforeTaxRatio;
	}

	public void setIncomeBeforeTaxRatio(String incomeBeforeTaxRatio) {
		this.incomeBeforeTaxRatio = incomeBeforeTaxRatio;
	}

	public String getIncomeTaxExpense() {
		return incomeTaxExpense;
	}

	public void setIncomeTaxExpense(String incomeTaxExpense) {
		this.incomeTaxExpense = incomeTaxExpense;
	}

	public String getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}

	public String getNetIncomeRatio() {
		return netIncomeRatio;
	}

	public void setNetIncomeRatio(String netIncomeRatio) {
		this.netIncomeRatio = netIncomeRatio;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public String getEpsdiluted() {
		return epsdiluted;
	}

	public void setEpsdiluted(String epsdiluted) {
		this.epsdiluted = epsdiluted;
	}

	public String getWeightedAverageShsOut() {
		return weightedAverageShsOut;
	}

	public void setWeightedAverageShsOut(String weightedAverageShsOut) {
		this.weightedAverageShsOut = weightedAverageShsOut;
	}

	public String getWeightedAverageShsOutDil() {
		return weightedAverageShsOutDil;
	}

	public void setWeightedAverageShsOutDil(String weightedAverageShsOutDil) {
		this.weightedAverageShsOutDil = weightedAverageShsOutDil;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFinalLink() {
		return finalLink;
	}

	public void setFinalLink(String finalLink) {
		this.finalLink = finalLink;
	}

	@Override
	public String toString() {
		return "FMP_AnnualIncomeStatement{" +
				"id=" + id +
				", dateMaj=" + dateMaj +
				", date='" + date + '\'' +
				", ticker='" + ticker + '\'' +
				", reportedCurrency='" + reportedCurrency + '\'' +
				", cik='" + cik + '\'' +
				", fillingDate='" + fillingDate + '\'' +
				", acceptedDate='" + acceptedDate + '\'' +
				", calendarYear='" + calendarYear + '\'' +
				", period='" + period + '\'' +
				", revenue='" + revenue + '\'' +
				", costOfRevenue='" + costOfRevenue + '\'' +
				", grossProfit='" + grossProfit + '\'' +
				", grossProfitRatio='" + grossProfitRatio + '\'' +
				", researchAndDevelopmentExpenses='" + researchAndDevelopmentExpenses + '\'' +
				", generalAndAdministrativeExpenses='" + generalAndAdministrativeExpenses + '\'' +
				", sellingAndMarketingExpenses='" + sellingAndMarketingExpenses + '\'' +
				", sellingGeneralAndAdministrativeExpenses='" + sellingGeneralAndAdministrativeExpenses + '\'' +
				", otherExpenses='" + otherExpenses + '\'' +
				", operatingExpenses='" + operatingExpenses + '\'' +
				", costAndExpenses='" + costAndExpenses + '\'' +
				", interestIncome='" + interestIncome + '\'' +
				", interestExpense='" + interestExpense + '\'' +
				", depreciationAndAmortization='" + depreciationAndAmortization + '\'' +
				", ebitda='" + ebitda + '\'' +
				", ebitdaratio='" + ebitdaratio + '\'' +
				", operatingIncome='" + operatingIncome + '\'' +
				", operatingIncomeRatio='" + operatingIncomeRatio + '\'' +
				", totalOtherIncomeExpensesNet='" + totalOtherIncomeExpensesNet + '\'' +
				", incomeBeforeTax='" + incomeBeforeTax + '\'' +
				", incomeBeforeTaxRatio='" + incomeBeforeTaxRatio + '\'' +
				", incomeTaxExpense='" + incomeTaxExpense + '\'' +
				", netIncome='" + netIncome + '\'' +
				", netIncomeRatio='" + netIncomeRatio + '\'' +
				", eps='" + eps + '\'' +
				", epsdiluted='" + epsdiluted + '\'' +
				", weightedAverageShsOut='" + weightedAverageShsOut + '\'' +
				", weightedAverageShsOutDil='" + weightedAverageShsOutDil + '\'' +
				", link='" + link + '\'' +
				", finalLink='" + finalLink + '\'' +
				'}';
	}
}
