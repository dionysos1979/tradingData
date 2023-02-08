package com.traderpatient.tradingdata.model.balanceSheet;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Date;

@Entity
public class BalanceSheet {

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

	// Format AlphaVantage function=BALANCE_SHEET
	private String fiscalDateEnding ;
	private String reportedCurrency ;
	private String totalAssets ;
	private String totalCurrentAssets ;
	private String cashAndCashEquivalentsAtCarryingValue ;
	private String cashAndShortTermInvestments ;
	private String inventory ;
	private String currentNetReceivables ;
	private String totalNonCurrentAssets ;
	private String propertyPlantEquipment ;
	private String accumulatedDepreciationAmortizationPPE ;
	private String intangibleAssets ;
	private String intangibleAssetsExcludingGoodwill ;
	private String goodwill ;
	private String investments ;
	private String longTermInvestments ;
	private String shortTermInvestments ;
	private String otherCurrentAssets ;
	private String otherNonCurrentAssets ;
	private String totalLiabilities ;
	private String totalCurrentLiabilities ;
	private String currentAccountsPayable ;
	private String deferredRevenue ;
	private String currentDebt ;
	private String shortTermDebt ;
	private String totalNonCurrentLiabilities ;
	private String capitalLeaseObligations ;
	private String longTermDebt ;
	private String currentLongTermDebt ;
	private String longTermDebtNoncurrent ;
	private String shortLongTermDebtTotal ;
	private String otherCurrentLiabilities ;
	private String otherNonCurrentLiabilities ;
	private String totalShareholderEquity ;
	private String treasuryStock ;
	private String retainedEarnings ;
	private String commonStock ;
	private String commonStockSharesOutstanding ;

	public BalanceSheet() {

	}

	public static Comparator<BalanceSheet> SORT_BY_FISCAL_DATE = new Comparator<BalanceSheet>() {
		public int compare(BalanceSheet one, BalanceSheet other) {
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

	public String getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}

	public String getTotalCurrentAssets() {
		return totalCurrentAssets;
	}

	public void setTotalCurrentAssets(String totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}

	public String getCashAndCashEquivalentsAtCarryingValue() {
		return cashAndCashEquivalentsAtCarryingValue;
	}

	public void setCashAndCashEquivalentsAtCarryingValue(String cashAndCashEquivalentsAtCarryingValue) {
		this.cashAndCashEquivalentsAtCarryingValue = cashAndCashEquivalentsAtCarryingValue;
	}

	public String getCashAndShortTermInvestments() {
		return cashAndShortTermInvestments;
	}

	public void setCashAndShortTermInvestments(String cashAndShortTermInvestments) {
		this.cashAndShortTermInvestments = cashAndShortTermInvestments;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getCurrentNetReceivables() {
		return currentNetReceivables;
	}

	public void setCurrentNetReceivables(String currentNetReceivables) {
		this.currentNetReceivables = currentNetReceivables;
	}

	public String getTotalNonCurrentAssets() {
		return totalNonCurrentAssets;
	}

	public void setTotalNonCurrentAssets(String totalNonCurrentAssets) {
		this.totalNonCurrentAssets = totalNonCurrentAssets;
	}

	public String getPropertyPlantEquipment() {
		return propertyPlantEquipment;
	}

	public void setPropertyPlantEquipment(String propertyPlantEquipment) {
		this.propertyPlantEquipment = propertyPlantEquipment;
	}

	public String getAccumulatedDepreciationAmortizationPPE() {
		return accumulatedDepreciationAmortizationPPE;
	}

	public void setAccumulatedDepreciationAmortizationPPE(String accumulatedDepreciationAmortizationPPE) {
		this.accumulatedDepreciationAmortizationPPE = accumulatedDepreciationAmortizationPPE;
	}

	public String getIntangibleAssets() {
		return intangibleAssets;
	}

	public void setIntangibleAssets(String intangibleAssets) {
		this.intangibleAssets = intangibleAssets;
	}

	public String getIntangibleAssetsExcludingGoodwill() {
		return intangibleAssetsExcludingGoodwill;
	}

	public void setIntangibleAssetsExcludingGoodwill(String intangibleAssetsExcludingGoodwill) {
		this.intangibleAssetsExcludingGoodwill = intangibleAssetsExcludingGoodwill;
	}

	public String getGoodwill() {
		return goodwill;
	}

	public void setGoodwill(String goodwill) {
		this.goodwill = goodwill;
	}

	public String getInvestments() {
		return investments;
	}

	public void setInvestments(String investments) {
		this.investments = investments;
	}

	public String getLongTermInvestments() {
		return longTermInvestments;
	}

	public void setLongTermInvestments(String longTermInvestments) {
		this.longTermInvestments = longTermInvestments;
	}

	public String getShortTermInvestments() {
		return shortTermInvestments;
	}

	public void setShortTermInvestments(String shortTermInvestments) {
		this.shortTermInvestments = shortTermInvestments;
	}

	public String getOtherCurrentAssets() {
		return otherCurrentAssets;
	}

	public void setOtherCurrentAssets(String otherCurrentAssets) {
		this.otherCurrentAssets = otherCurrentAssets;
	}

	public String getOtherNonCurrentAssets() {
		return otherNonCurrentAssets;
	}

	public void setOtherNonCurrentAssets(String otherNonCurrentAssets) {
		this.otherNonCurrentAssets = otherNonCurrentAssets;
	}

	public String getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(String totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public String getTotalCurrentLiabilities() {
		return totalCurrentLiabilities;
	}

	public void setTotalCurrentLiabilities(String totalCurrentLiabilities) {
		this.totalCurrentLiabilities = totalCurrentLiabilities;
	}

	public String getCurrentAccountsPayable() {
		return currentAccountsPayable;
	}

	public void setCurrentAccountsPayable(String currentAccountsPayable) {
		this.currentAccountsPayable = currentAccountsPayable;
	}

	public String getDeferredRevenue() {
		return deferredRevenue;
	}

	public void setDeferredRevenue(String deferredRevenue) {
		this.deferredRevenue = deferredRevenue;
	}

	public String getCurrentDebt() {
		return currentDebt;
	}

	public void setCurrentDebt(String currentDebt) {
		this.currentDebt = currentDebt;
	}

	public String getShortTermDebt() {
		return shortTermDebt;
	}

	public void setShortTermDebt(String shortTermDebt) {
		this.shortTermDebt = shortTermDebt;
	}

	public String getTotalNonCurrentLiabilities() {
		return totalNonCurrentLiabilities;
	}

	public void setTotalNonCurrentLiabilities(String totalNonCurrentLiabilities) {
		this.totalNonCurrentLiabilities = totalNonCurrentLiabilities;
	}

	public String getCapitalLeaseObligations() {
		return capitalLeaseObligations;
	}

	public void setCapitalLeaseObligations(String capitalLeaseObligations) {
		this.capitalLeaseObligations = capitalLeaseObligations;
	}

	public String getLongTermDebt() {
		return longTermDebt;
	}

	public void setLongTermDebt(String longTermDebt) {
		this.longTermDebt = longTermDebt;
	}

	public String getCurrentLongTermDebt() {
		return currentLongTermDebt;
	}

	public void setCurrentLongTermDebt(String currentLongTermDebt) {
		this.currentLongTermDebt = currentLongTermDebt;
	}

	public String getLongTermDebtNoncurrent() {
		return longTermDebtNoncurrent;
	}

	public void setLongTermDebtNoncurrent(String longTermDebtNoncurrent) {
		this.longTermDebtNoncurrent = longTermDebtNoncurrent;
	}

	public String getShortLongTermDebtTotal() {
		return shortLongTermDebtTotal;
	}

	public void setShortLongTermDebtTotal(String shortLongTermDebtTotal) {
		this.shortLongTermDebtTotal = shortLongTermDebtTotal;
	}

	public String getOtherCurrentLiabilities() {
		return otherCurrentLiabilities;
	}

	public void setOtherCurrentLiabilities(String otherCurrentLiabilities) {
		this.otherCurrentLiabilities = otherCurrentLiabilities;
	}

	public String getOtherNonCurrentLiabilities() {
		return otherNonCurrentLiabilities;
	}

	public void setOtherNonCurrentLiabilities(String otherNonCurrentLiabilities) {
		this.otherNonCurrentLiabilities = otherNonCurrentLiabilities;
	}

	public String getTotalShareholderEquity() {
		return totalShareholderEquity;
	}

	public void setTotalShareholderEquity(String totalShareholderEquity) {
		this.totalShareholderEquity = totalShareholderEquity;
	}

	public String getTreasuryStock() {
		return treasuryStock;
	}

	public void setTreasuryStock(String treasuryStock) {
		this.treasuryStock = treasuryStock;
	}

	public String getRetainedEarnings() {
		return retainedEarnings;
	}

	public void setRetainedEarnings(String retainedEarnings) {
		this.retainedEarnings = retainedEarnings;
	}

	public String getCommonStock() {
		return commonStock;
	}

	public void setCommonStock(String commonStock) {
		this.commonStock = commonStock;
	}

	public String getCommonStockSharesOutstanding() {
		return commonStockSharesOutstanding;
	}

	public void setCommonStockSharesOutstanding(String commonStockSharesOutstanding) {
		this.commonStockSharesOutstanding = commonStockSharesOutstanding;
	}

}
