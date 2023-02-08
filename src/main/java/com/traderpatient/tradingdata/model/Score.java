package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Score {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    private Date date;
    private Date dateMaj;
	private String ticker;

	// Quarterly Earnings
	private Date fiscalDateEnding;
	private Date reportedDate;
	private Double reportedEPS;
	private Double estimatedEPS;
	private Double surprise;
	private Double surprisePercentage;
	private Double epsGrowthQ;
	private Double epsGrowthQ_1;
	private Double epsGrowthQ_2;
	private Double epsGrowthQExpected;
	private Double epsGrowthQExpectedNextQuarter;

	// Quarterly Revenues
	private Double revenueQ;
	private Double revenueQExpected;
	private Double revenueExpectedNextQuarter;
	private Double revenueGrowthQ;
	private Double revenueGrowthQ_1;
	private Double revenueGrowthQ_2;

	// Annual Earnings
	private Double revenueY;
	private Double revenueExpectedNextYear;

	// Fundamental Datas
	private Double nbSharesOutstandingQ;
	private Double nbSharesOutstandingGrowthQoQ;
	private Double nbSharesOutstandingY;
	private Double nbSharesOutstandingGrowthYoY;

	// Debts


	// Ratios
	private Double grossMargin;
	private Double operatingMargin;
	private Double netMargin;
	private Double roe;
	private Double roi;

	// Technical datas
	private Double perfDay;	 		private Double rank_perfDay;
	private Double perfWeek;		private Double rank_perfWeek;
	private Double perfMonth;		private Double rank_perfMonth;
	private Double perf3Month;		private Double rank_perf3Month;
	private Double perf6Month;		private Double rank_perf6Month;
	private Double rsDay;			private Double rank_rsDay;
	private Double rsWeek;			private Double rank_rsWeek;
	private Double rsMonth;			private Double rank_rsMonth;
	private Double rs3Month;		private Double rank_rs3Month;
	private Double rs6Month;		private Double rank_rs6Month;

	// Global score
	private Double score;
	private Double rank100;

	public Score(String ticker,
				 Date date,
				 Double closeToday,
				 Double closeYesterday,
				 Double closeWeek,
				 Double closeMonth,
				 Double close3Month,
				 Double reportedEPS,
				 Date reportedDate,
				 Double estimatedEPS,
				 Date fiscalDateEnding,
				 Double surprise,
				 Double surprisePercentage,
				 Double epsGrowthQ,
				 Double epsGrowthQ_1,
				 Double epsGrowthQ_2,
				 Double perfIndiceDay,
				 Double perfIndiceWeek,
				 Double perfIndiceMonth,
				 Double perfIndice3Month,
				 Double revenueQ,
				 Double revenueY,
				 Double revenueGrowthQ,
				 Double revenueGrowthQ_1,
				 Double revenueGrowthQ_2,
				 Double roe,
				 Double grossMargin,
				 Double netMargin,
				 Double operatingMargin) {
		this.ticker = ticker;
		this.date = date;
        this.dateMaj = new Date();
		this.perfDay   = Double.valueOf(closeToday - closeYesterday ) / closeYesterday;
		this.perfWeek  = Double.valueOf(closeToday - closeWeek ) / closeWeek;
		this.perfMonth  = Double.valueOf(closeToday - closeMonth ) / closeMonth;
		this.perf3Month  = Double.valueOf(closeToday - close3Month ) / close3Month;
		this.reportedEPS = reportedEPS;
		this.reportedDate = reportedDate;
		this.estimatedEPS = estimatedEPS;
		this.fiscalDateEnding = fiscalDateEnding;
		this.surprise = surprise;
		this.surprisePercentage = surprisePercentage;
		this.epsGrowthQ = epsGrowthQ;
		this.epsGrowthQ_1 = epsGrowthQ_1;
		this.epsGrowthQ_2 = epsGrowthQ_2;
		this.rsDay = perfDay - perfIndiceDay;
		this.rsWeek = perfWeek - perfIndiceWeek;
		this.rsMonth = perfMonth - perfIndiceMonth;
		this.rs3Month = perf3Month - perfIndice3Month;
		this.revenueQ = revenueQ;
		this.revenueY = revenueY;
		this.revenueGrowthQ = revenueGrowthQ;
		this.revenueGrowthQ_1 = revenueGrowthQ_1;
		this.revenueGrowthQ_2 = revenueGrowthQ_2;
		this.roe = roe;
		this.grossMargin = grossMargin;
		this.netMargin = netMargin;
		this.operatingMargin = operatingMargin;
	}

	public Score() {

	}

	@Override
	public String toString() {
		return "Score{" +
				"id=" + id +
				", date=" + date +
				", ticker='" + ticker + '\'' +
				", perfDay=" + perfDay +
				", perfWeek=" + perfWeek +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Date getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}

	public Double getReportedEPS() {
		return reportedEPS;
	}

	public void setReportedEPS(Double reportedEPS) {
		this.reportedEPS = reportedEPS;
	}

	public Double getEstimatedEPS() {
		return estimatedEPS;
	}

	public void setEstimatedEPS(Double estimatedEPS) {
		this.estimatedEPS = estimatedEPS;
	}

	public Double getSurprise() {
		return surprise;
	}

	public void setSurprise(Double surprise) {
		this.surprise = surprise;
	}

	public Double getSurprisePercentage() {
		return surprisePercentage;
	}

	public void setSurprisePercentage(Double surprisePercentage) {
		this.surprisePercentage = surprisePercentage;
	}

	public Double getEpsGrowthQ() {
		return epsGrowthQ;
	}

	public void setEpsGrowthQ(Double epsGrowthQ) {
		this.epsGrowthQ = epsGrowthQ;
	}

	public Double getEpsGrowthQ_1() {
		return epsGrowthQ_1;
	}

	public void setEpsGrowthQ_1(Double epsGrowthQ_1) {
		this.epsGrowthQ_1 = epsGrowthQ_1;
	}

	public Double getEpsGrowthQ_2() {
		return epsGrowthQ_2;
	}

	public void setEpsGrowthQ_2(Double epsGrowthQ_2) {
		this.epsGrowthQ_2 = epsGrowthQ_2;
	}

	public Double getEpsGrowthQExpected() {
		return epsGrowthQExpected;
	}

	public void setEpsGrowthQExpected(Double epsGrowthQExpected) {
		this.epsGrowthQExpected = epsGrowthQExpected;
	}

	public Double getEpsGrowthQExpectedNextQuarter() {
		return epsGrowthQExpectedNextQuarter;
	}

	public void setEpsGrowthQExpectedNextQuarter(Double epsGrowthQExpectedNextQuarter) {
		this.epsGrowthQExpectedNextQuarter = epsGrowthQExpectedNextQuarter;
	}

	public Double getRevenueQ() {
		return revenueQ;
	}

	public void setRevenueQ(Double revenueQ) {
		this.revenueQ = revenueQ;
	}

	public Double getRevenueQExpected() {
		return revenueQExpected;
	}

	public void setRevenueQExpected(Double revenueQExpected) {
		this.revenueQExpected = revenueQExpected;
	}

	public Double getRevenueExpectedNextQuarter() {
		return revenueExpectedNextQuarter;
	}

	public void setRevenueExpectedNextQuarter(Double revenueExpectedNextQuarter) {
		this.revenueExpectedNextQuarter = revenueExpectedNextQuarter;
	}

	public Double getRevenueY() {
		return revenueY;
	}

	public void setRevenueY(Double revenueY) {
		this.revenueY = revenueY;
	}

	public Double getRevenueExpectedNextYear() {
		return revenueExpectedNextYear;
	}

	public void setRevenueExpectedNextYear(Double revenueExpectedNextYear) {
		this.revenueExpectedNextYear = revenueExpectedNextYear;
	}

	public Double getNbSharesOutstandingQ() {
		return nbSharesOutstandingQ;
	}

	public void setNbSharesOutstandingQ(Double nbSharesOutstandingQ) {
		this.nbSharesOutstandingQ = nbSharesOutstandingQ;
	}

	public Double getNbSharesOutstandingGrowthQoQ() {
		return nbSharesOutstandingGrowthQoQ;
	}

	public void setNbSharesOutstandingGrowthQoQ(Double nbSharesOutstandingGrowthQoQ) {
		this.nbSharesOutstandingGrowthQoQ = nbSharesOutstandingGrowthQoQ;
	}

	public Double getNbSharesOutstandingY() {
		return nbSharesOutstandingY;
	}

	public void setNbSharesOutstandingY(Double nbSharesOutstandingY) {
		this.nbSharesOutstandingY = nbSharesOutstandingY;
	}

	public Double getNbSharesOutstandingGrowthYoY() {
		return nbSharesOutstandingGrowthYoY;
	}

	public void setNbSharesOutstandingGrowthYoY(Double nbSharesOutstandingGrowthYoY) {
		this.nbSharesOutstandingGrowthYoY = nbSharesOutstandingGrowthYoY;
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

	public Double getPerfDay() {
		return perfDay;
	}

	public void setPerfDay(Double perfDay) {
		this.perfDay = perfDay;
	}

	public Double getRank_perfDay() {
		return rank_perfDay;
	}

	public void setRank_perfDay(Double rank_perfDay) {
		this.rank_perfDay = rank_perfDay;
	}

	public Double getPerfWeek() {
		return perfWeek;
	}

	public void setPerfWeek(Double perfWeek) {
		this.perfWeek = perfWeek;
	}

	public Double getRank_perfWeek() {
		return rank_perfWeek;
	}

	public void setRank_perfWeek(Double rank_perfWeek) {
		this.rank_perfWeek = rank_perfWeek;
	}

	public Double getPerfMonth() {
		return perfMonth;
	}

	public void setPerfMonth(Double perfMonth) {
		this.perfMonth = perfMonth;
	}

	public Double getRank_perfMonth() {
		return rank_perfMonth;
	}

	public void setRank_perfMonth(Double rank_perfMonth) {
		this.rank_perfMonth = rank_perfMonth;
	}

	public Double getPerf3Month() {
		return perf3Month;
	}

	public void setPerf3Month(Double perf3Month) {
		this.perf3Month = perf3Month;
	}

	public Double getRank_perf3Month() {
		return rank_perf3Month;
	}

	public void setRank_perf3Month(Double rank_perf3Month) {
		this.rank_perf3Month = rank_perf3Month;
	}

	public Double getPerf6Month() {
		return perf6Month;
	}

	public void setPerf6Month(Double perf6Month) {
		this.perf6Month = perf6Month;
	}

	public Double getRank_perf6Month() {
		return rank_perf6Month;
	}

	public void setRank_perf6Month(Double rank_perf6Month) {
		this.rank_perf6Month = rank_perf6Month;
	}

	public Double getRsDay() {
		return rsDay;
	}

	public void setRsDay(Double rsDay) {
		this.rsDay = rsDay;
	}

	public Double getRank_rsDay() {
		return rank_rsDay;
	}

	public void setRank_rsDay(Double rank_rsDay) {
		this.rank_rsDay = rank_rsDay;
	}

	public Double getRsWeek() {
		return rsWeek;
	}

	public void setRsWeek(Double rsWeek) {
		this.rsWeek = rsWeek;
	}

	public Double getRank_rsWeek() {
		return rank_rsWeek;
	}

	public void setRank_rsWeek(Double rank_rsWeek) {
		this.rank_rsWeek = rank_rsWeek;
	}

	public Double getRsMonth() {
		return rsMonth;
	}

	public void setRsMonth(Double rsMonth) {
		this.rsMonth = rsMonth;
	}

	public Double getRank_rsMonth() {
		return rank_rsMonth;
	}

	public void setRank_rsMonth(Double rank_rsMonth) {
		this.rank_rsMonth = rank_rsMonth;
	}

	public Double getRs3Month() {
		return rs3Month;
	}

	public void setRs3Month(Double rs3Month) {
		this.rs3Month = rs3Month;
	}

	public Double getRank_rs3Month() {
		return rank_rs3Month;
	}

	public void setRank_rs3Month(Double rank_rs3Month) {
		this.rank_rs3Month = rank_rs3Month;
	}

	public Double getRs6Month() {
		return rs6Month;
	}

	public void setRs6Month(Double rs6Month) {
		this.rs6Month = rs6Month;
	}

	public Double getRank_rs6Month() {
		return rank_rs6Month;
	}

	public void setRank_rs6Month(Double rank_rs6Month) {
		this.rank_rs6Month = rank_rs6Month;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getRank100() {
		return rank100;
	}

	public void setRank100(Double rank100) {
		this.rank100 = rank100;
	}

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }

	public Double getRevenueGrowthQ() {
		return revenueGrowthQ;
	}

	public void setRevenueGrowthQ(Double revenueGrowthQ) {
		this.revenueGrowthQ = revenueGrowthQ;
	}

	public Double getRevenueGrowthQ_1() {
		return revenueGrowthQ_1;
	}

	public void setRevenueGrowthQ_1(Double revenueGrowthQ_1) {
		this.revenueGrowthQ_1 = revenueGrowthQ_1;
	}

	public Double getRevenueGrowthQ_2() {
		return revenueGrowthQ_2;
	}

	public void setRevenueGrowthQ_2(Double revenueGrowthQ_2) {
		this.revenueGrowthQ_2 = revenueGrowthQ_2;
	}
}
