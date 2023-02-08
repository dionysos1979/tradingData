package com.traderpatient.tradingdata.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CompteurRequete {
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String siteWeb;
	private String apikey;
	private Double nbRequetes;

	public CompteurRequete(Integer id) {
		this.id = id;
	}

	public CompteurRequete() {

	}

	public CompteurRequete(Date date, String siteWeb, String apikey, Double nbRequetes) {
		this.date = date;
		this.siteWeb = siteWeb;
		this.apikey = apikey;
		this.nbRequetes = nbRequetes;
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

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public Double getNbRequetes() {
		return nbRequetes;
	}

	public void setNbRequetes(Double nbRequetes) {
		this.nbRequetes = nbRequetes;
	}

	@Override
	public String toString() {
		return "CompteurRequete{" +
				"id=" + id +
				", date=" + date +
				", siteWeb='" + siteWeb + '\'' +
				", apikey='" + apikey + '\'' +
				", nbRequetes=" + nbRequetes +
				'}';
	}
}
