package com.traderpatient.tradingdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@JsonIgnoreProperties
public class Polygon_Quote implements Comparable {
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@JsonProperty(value= "T")
	private String ticker;
	private Double v;
	@JsonProperty(value= "c")
	private Double close;
	@JsonProperty(value= "h")
	private Double high;
	@JsonProperty(value= "l")
	private Double low;
	@JsonProperty(value= "o")
	private Double open;

	@JsonProperty(value= "t")
	//The Unix Msec timestamp for the start of the aggregate window.
	private Double timeStamp;

	@JsonProperty(value= "n")
	private Double numberTransactions;

	@JsonProperty(value= "vw")
	//The volume weighted average price.
	private Double volumeWAP;

	private Integer cotationId;
	private Date date;

	public Polygon_Quote() {
		super();
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Double timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getNumberTransactions() {
		return numberTransactions;
	}

	public void setNumberTransactions(Double numberTransactions) {
		this.numberTransactions = numberTransactions;
	}

	public Double getVolumeWAP() {
		return volumeWAP;
	}

	public void setVolumeWAP(Double volumeWAP) {
		this.volumeWAP = volumeWAP;
	}

	public Double getV() {
		return v;
	}

	public void setV(Double v) {
		this.v = v;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCotationId() {
		return cotationId;
	}

	public void setCotationId(Integer cotationId) {
		this.cotationId = cotationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Object p) {
		Polygon_Quote that = (Polygon_Quote) p;
		return that.getDate().compareTo(this.getDate());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Polygon_Quote that = (Polygon_Quote) o;
		return Objects.equals(ticker, that.ticker) && Objects.equals(v, that.v)
				&& Objects.equals(close, that.close) && Objects.equals(high, that.high)
				&& Objects.equals(low, that.low) && Objects.equals(open, that.open)
				&& Objects.equals(date, that.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, ticker, v, close, high, low, open, date);
	}

	@Override
	public String toString() {
		return "Quote{" +
				"id=" + id +
				", ticker='" + ticker + '\'' +
				", v=" + v +
				", close=" + close +
				", high=" + high +
				", low=" + low +
				", open=" + open +
				", timeStamp=" + timeStamp +
				", numberTransactions=" + numberTransactions +
				", volumeWAP=" + volumeWAP +
				", cotationId=" + cotationId +
				", date=" + date +
				'}';
	}

}
