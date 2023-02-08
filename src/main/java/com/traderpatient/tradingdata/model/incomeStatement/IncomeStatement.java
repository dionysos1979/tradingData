package com.traderpatient.tradingdata.model.incomeStatement;


import com.traderpatient.tradingdata.model.FMP_AnnualIncomeStatement;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_AnnualReport;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity
public class IncomeStatement {
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
	private String grossProfit ;
	private String totalRevenue ;
	private String costOfRevenue ;
	private String costofGoodsAndServicesSold ;
	private String operatingIncome ;
	private String sellingGeneralAndAdministrative ;
	private String researchAndDevelopment ;
	private String operatingExpenses ;
	private String investmentIncomeNet ;
	private String netInterestIncome ;
	private String interestIncome ;
	private String interestExpense ;
	private String nonInterestIncome ;
	private String otherNonOperatingIncome ;
	private String depreciation ;
	private String depreciationAndAmortization ;
	private String incomeBeforeTax ;
	private String incomeTaxExpense ;
	private String interestAndDebtExpense ;
	private String netIncomeFromContinuingOperations ;
	private String comprehensiveIncomeNetOfTax ;
	private String ebit ;
	private String ebitda ;
	private String netIncome ;

	public IncomeStatement() {

	}

	public IncomeStatement(AlphaVantage_AnnualReport annualReport) {
		this.reportedCurrency  = annualReport.getReportedCurrency();
		this.grossProfit  = annualReport.getGrossProfit();
		this.totalRevenue  = annualReport.getTotalRevenue();
		this.costOfRevenue  = annualReport.getCostOfRevenue();
		this.costofGoodsAndServicesSold  = annualReport.getCostofGoodsAndServicesSold();
		this.operatingIncome  = annualReport.getOperatingIncome();
		this.sellingGeneralAndAdministrative  = annualReport.getSellingGeneralAndAdministrative();
		this.researchAndDevelopment  = annualReport.getResearchAndDevelopment();
		this.operatingExpenses  = annualReport.getOperatingExpenses();
		this.investmentIncomeNet  = annualReport.getInvestmentIncomeNet();
		this.netInterestIncome  = annualReport.getNetInterestIncome();
		this.interestIncome  = annualReport.getInterestIncome();
		this.interestExpense  = annualReport.getInterestExpense();
		this.nonInterestIncome  = annualReport.getNonInterestIncome();
		this.otherNonOperatingIncome  = annualReport.getOtherNonOperatingIncome();
		this.depreciation  = annualReport.getDepreciation();
		this.depreciationAndAmortization  = annualReport.getDepreciationAndAmortization();
		this.incomeBeforeTax  = annualReport.getIncomeBeforeTax();
		this.incomeTaxExpense  = annualReport.getIncomeTaxExpense();
		this.interestAndDebtExpense  = annualReport.getInterestAndDebtExpense();
		this.netIncomeFromContinuingOperations  = annualReport.getNetIncomeFromContinuingOperations();
		this.comprehensiveIncomeNetOfTax  = annualReport.getComprehensiveIncomeNetOfTax();
		this.ebit  = annualReport.getEbit();
		this.ebitda  = annualReport.getEbitda();
		this.netIncome  = annualReport.getNetIncome();
	}


	public IncomeStatement(AlphaVantage_QuarterlyReport annualReport) {
		this.reportedCurrency  = annualReport.getReportedCurrency();
		this.grossProfit  = annualReport.getGrossProfit();
		this.totalRevenue  = annualReport.getTotalRevenue();
		this.costOfRevenue  = annualReport.getCostOfRevenue();
		this.costofGoodsAndServicesSold  = annualReport.getCostofGoodsAndServicesSold();
		this.operatingIncome  = annualReport.getOperatingIncome();
		this.sellingGeneralAndAdministrative  = annualReport.getSellingGeneralAndAdministrative();
		this.researchAndDevelopment  = annualReport.getResearchAndDevelopment();
		this.operatingExpenses  = annualReport.getOperatingExpenses();
		this.investmentIncomeNet  = annualReport.getInvestmentIncomeNet();
		this.netInterestIncome  = annualReport.getNetInterestIncome();
		this.interestIncome  = annualReport.getInterestIncome();
		this.interestExpense  = annualReport.getInterestExpense();
		this.nonInterestIncome  = annualReport.getNonInterestIncome();
		this.otherNonOperatingIncome  = annualReport.getOtherNonOperatingIncome();
		this.depreciation  = annualReport.getDepreciation();
		this.depreciationAndAmortization  = annualReport.getDepreciationAndAmortization();
		this.incomeBeforeTax  = annualReport.getIncomeBeforeTax();
		this.incomeTaxExpense  = annualReport.getIncomeTaxExpense();
		this.interestAndDebtExpense  = annualReport.getInterestAndDebtExpense();
		this.netIncomeFromContinuingOperations  = annualReport.getNetIncomeFromContinuingOperations();
		this.comprehensiveIncomeNetOfTax  = annualReport.getComprehensiveIncomeNetOfTax();
		this.ebit  = annualReport.getEbit();
		this.ebitda  = annualReport.getEbitda();
		this.netIncome  = annualReport.getNetIncome();
	}

	public IncomeStatement(FMP_AnnualIncomeStatement annualReport) {
		this.reportedCurrency  = annualReport.getReportedCurrency();
		this.grossProfit  = annualReport.getGrossProfit();
		this.totalRevenue  = annualReport.getRevenue();
//		this.costOfRevenue  = annualReport.getCostOfRevenue(); // Montant mauvais !!!
		this.costofGoodsAndServicesSold  = annualReport.getCostOfRevenue(); // Montant mauvais !!!
		this.operatingIncome  = annualReport.getOperatingIncome();
		this.sellingGeneralAndAdministrative  = annualReport.getSellingGeneralAndAdministrativeExpenses();
		this.researchAndDevelopment  = annualReport.getResearchAndDevelopmentExpenses();
		this.operatingExpenses  = annualReport.getOperatingExpenses();
		this.investmentIncomeNet  = annualReport.getInterestIncome();
//		this.netInterestIncome  = annualReport.getNetInterestIncome();
		this.interestIncome  = annualReport.getInterestIncome();
		this.interestExpense  = annualReport.getInterestExpense();
//		this.nonInterestIncome  = annualReport.getNonInterestIncome();
//		this.otherNonOperatingIncome  = annualReport.getOtherNonOperatingIncome();
//		this.depreciation  = annualReport.getd;
		this.depreciationAndAmortization  = annualReport.getDepreciationAndAmortization();
		this.incomeBeforeTax  = annualReport.getIncomeBeforeTax();
		this.incomeTaxExpense  = annualReport.getIncomeTaxExpense();
		this.interestAndDebtExpense  = annualReport.getInterestExpense();
//		this.netIncomeFromContinuingOperations  = annualReport.getNetIncomeFromContinuingOperations();
//		this.comprehensiveIncomeNetOfTax  = annualReport.getComprehensiveIncomeNetOfTax();
//		this.ebit  = annualReport.get();
		this.ebitda  = annualReport.getEbitda();
		this.netIncome  = annualReport.getNetIncome();
		// Données spécifiques FMP :

	}

	public static Comparator<IncomeStatement> SORT_BY_FISCAL_DATE = new Comparator<IncomeStatement>() {
		public int compare(IncomeStatement one, IncomeStatement other) {
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

	@Override
	public String toString() {
		return "IncomeStatement{" +
				"id=" + id +
				", ticker='" + ticker + '\'' +
				", date=" + date +
				", dateMaj=" + dateMaj +
				", frequence='" + frequence + '\'' +
				", dataProvider='" + dataProvider + '\'' +
				", fiscalDateEnding='" + fiscalDateEnding + '\'' +
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IncomeStatement that = (IncomeStatement) o;
		return Objects.equals(ticker, that.ticker)
				&& Objects.equals(frequence, that.frequence)
//				&& Objects.equals(fiscalDateEnding, that.fiscalDateEnding)
				&& Objects.equals(reportedCurrency, that.reportedCurrency)
				&& Objects.equals(grossProfit, that.grossProfit)
//				&& Objects.equals(totalRevenue, that.totalRevenue)
//				&& Objects.equals(costOfRevenue, that.costOfRevenue)
//				&& Objects.equals(costofGoodsAndServicesSold, that.costofGoodsAndServicesSold)
//				&& Objects.equals(operatingIncome, that.operatingIncome)
				&& Objects.equals(sellingGeneralAndAdministrative, that.sellingGeneralAndAdministrative)
				&& Objects.equals(researchAndDevelopment, that.researchAndDevelopment)
				&& Objects.equals(operatingExpenses, that.operatingExpenses)
//				&& Objects.equals(investmentIncomeNet, that.investmentIncomeNet)
//				&& Objects.equals(netInterestIncome, that.netInterestIncome)
//				&& Objects.equals(interestIncome, that.interestIncome)
				&& Objects.equals(interestExpense, that.interestExpense)
//				&& Objects.equals(nonInterestIncome, that.nonInterestIncome)
//				&& Objects.equals(otherNonOperatingIncome, that.otherNonOperatingIncome)
//				&& Objects.equals(depreciation, that.depreciation)
				&& Objects.equals(depreciationAndAmortization, that.depreciationAndAmortization)
				&& Objects.equals(incomeBeforeTax, that.incomeBeforeTax)
//				&& Objects.equals(incomeTaxExpense, that.incomeTaxExpense)
//				&& Objects.equals(interestAndDebtExpense, that.interestAndDebtExpense)
//				&& Objects.equals(netIncomeFromContinuingOperations, that.netIncomeFromContinuingOperations)
//				&& Objects.equals(comprehensiveIncomeNetOfTax, that.comprehensiveIncomeNetOfTax)
				&& Objects.equals(ebit, that.ebit) && Objects.equals(ebitda, that.ebitda)
				&& Objects.equals(netIncome, that.netIncome);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticker, frequence, fiscalDateEnding, reportedCurrency, grossProfit, totalRevenue, costOfRevenue, costofGoodsAndServicesSold, operatingIncome, sellingGeneralAndAdministrative, researchAndDevelopment, operatingExpenses, investmentIncomeNet, netInterestIncome, interestIncome, interestExpense, nonInterestIncome, otherNonOperatingIncome, depreciation, depreciationAndAmortization, incomeBeforeTax, incomeTaxExpense, interestAndDebtExpense, netIncomeFromContinuingOperations, comprehensiveIncomeNetOfTax, ebit, ebitda, netIncome);
	}
}
