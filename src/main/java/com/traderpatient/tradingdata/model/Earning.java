package com.traderpatient.tradingdata.model;

import com.traderpatient.tradingdata.model.base.BaseEntity;
import com.traderpatient.tradingdata.service.FinvizService;
import jakarta.persistence.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

@Entity
@MappedSuperclass
public class Earning  extends BaseEntity {

	private static char BILLION_DIGIT = 'B';
	private static char MILLION_DIGIT = 'M';

    @Temporal(TemporalType.DATE)
	private Calendar date;
    
    @Temporal(TemporalType.DATE)
	private Calendar dateMAJ;
	private int quarter; // 1 à 4 cf LocalDate.now().get(IsoFields.QUARTER_OF_YEAR)
	
//	@ManyToOne
//	private Year year; 
	
	@OneToOne
	private Stock stock;
	
	@ManyToOne
	private Currency currency;
	
	@ManyToOne
	private FinancialSource financialSource;
	
	HashMap<String, String> data = new HashMap<String, String>();
	/**
	 * EPS
	 */
	private Double epsGrowthThisQuarter;
	private Double epsGrowthThisQuarterExpected;
	private Double epsGrowthThisQuarterExpectedNextQuarter;
	
	/**
	 * REVENUE
	 */
	private Double revenueThisYear;
	private Double revenueExpected;
	private Double revenueExpectedNextQuarter;
	
	/**
	 * INCOME
	 */
	private Double incomeThisYear;
	private Double incomeThisQuarter;
	
	/**
	 * MARGINS
	 */
	private Double grossMargin;
	private Double operatingMargin;
	private Double netMargin;
	private Double roe;
	private Double roi;
	
	/**
	 * Constructeur
	 */
	public Earning() {
		this.dateMAJ = Calendar.getInstance();
	}

	public int getQuarter() {
		return quarter;
	}


	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}


//	public Year getYear() {
//		return year;
//	}
//
//
//	public void setYear(Year year) {
//		this.year = year;
//	}


	public Stock getStock() {
		return stock;
	}


	public void setStock(Stock stock) {
		this.stock = stock;
	}
//
//
//	public Currency getCurrency() {
//		return currency;
//	}
//
//
//	public void setCurrency(Currency currency) {
//		this.currency = currency;
//	}


	public FinancialSource getFinancialSource() {
		return financialSource;
	}


	public void setFinancialSource(FinancialSource financialSource) {
		this.financialSource = financialSource;
	}


	
	public Double getIncomeThisYear() {
		// Cas de FINVIZ
		return  FinvizService.convertDigit(data.get("Income"));
	}

	public void setIncomeThisYear(Double incomeThisYear) {
		this.incomeThisYear = incomeThisYear;
	}

	public Double getIncomeThisQuarter() {
		return incomeThisQuarter;
	}

	public void setIncomeThisQuarter(Double incomeThisQuarter) {
		this.incomeThisQuarter = incomeThisQuarter;
	}

	public Double getRevenueThisYear() {
		// Cas de FINVIZ
		return  FinvizService.convertDigit(data.get("Sales"));
		
		// Format affichage :
		//System.out.printf(Locale.FRANCE, "Earning().getRevenue():[%,f]\n",   stock.getEarning().getRevenueThisYear());
	}

	public void setRevenueThisYear(Double revenueThisYear) {
		this.revenueThisYear = revenueThisYear;
	}

	public Double getRevenueExpected() {
		return revenueExpected;
	}


	public void setRevenueExpected(Double revenueExpected) {
		this.revenueExpected = revenueExpected;
	}


	public Double getRevenueExpectedNextQuarter() {
		return revenueExpectedNextQuarter;
	}


	public void setRevenueExpectedNextQuarter(Double revenueExpectedNextQuarter) {
		this.revenueExpectedNextQuarter = revenueExpectedNextQuarter;
	}


	public Double getGrossMargin() {
		return grossMargin;
	}


	public void setGrossMargin(Double grossMargin) {
		this.grossMargin = grossMargin;
	}


	public Double getOperatingMargin() {
		return operatingMargin;
	}


	public void setOperatingMargin(Double operatingMargin) {
		this.operatingMargin = operatingMargin;
	}


	public Double getNetMargin() {
		return netMargin;
	}


	public void setNetMargin(Double netMargin) {
		this.netMargin = netMargin;
	}


	public Double getRoe() {
		return roe;
	}


	public void setRoe(Double roe) {
		this.roe = roe;
	}


	public Double getRoi() {
		return roi;
	}


	public void setRoi(Double roi) {
		this.roi = roi;
	}



	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getDateMAJ() {
		return dateMAJ;
	}

	public void setDateMAJ(Calendar dateMAJ) {
		this.dateMAJ = dateMAJ;
	}

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}


}
