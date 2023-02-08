package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Polygon_DailyQuote {
	@Id
	@Column(name = "cotation_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cotationId;

	@Temporal(TemporalType.DATE)
	private Date date;
	private String adjusted;
	private Integer count;
	private Integer queryCount;
	private String request_id;
	private Integer resultsCount;
	private String status;
	private Date maj;

	@Transient
	private List<Polygon_Quote> results = new ArrayList<Polygon_Quote>();

	public Polygon_DailyQuote() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAdjusted() {
		return adjusted;
	}

	public void setAdjusted(String adjusted) {
		this.adjusted = adjusted;
	}

	public Integer getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public Integer getResultsCount() {
		return resultsCount;
	}

	public void setResultsCount(Integer resultsCount) {
		this.resultsCount = resultsCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getMaj() {
		return maj;
	}

	public void setMaj(Date maj) {
		this.maj = maj;
	}

	public Integer getCotationId() {
		return cotationId;
	}

	public void setCotationId(Integer cotationId) {
		this.cotationId = cotationId;
	}

	public List<Polygon_Quote> getResults() {
		return results;
	}

	public void setResults(List<Polygon_Quote> results) {
		this.results = results;
	}

	/**
	 * true si il existe des cotations ce jour
	 * false si SAM / DIM ou marché fermé
	 * @return
	 */
	public boolean isOpenMarket(){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getDate());

		return getQueryCount() != 0
				&& getResultsCount() != 0
				&& !(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
				(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) );
	}
	@Override
	public String toString() {
		return "DailyQuote{" +
				"cotationId=" + cotationId +
				", date=" + date +
				", adjusted='" + adjusted + '\'' +
				", count=" + count +
				", queryCount=" + queryCount +
				", request_id='" + request_id + '\'' +
				", resultsCount=" + resultsCount +
				", status='" + status + '\'' +
				", maj=" + maj +
				'}';
	}
}
