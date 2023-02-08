package com.traderpatient.tradingdata.model;

import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class FinancialSource extends BaseEntity {

	private String urlEarnings;

	@ManyToOne
	private DataProvider siteWeb;

	@ManyToOne
	private Stock stock;

	public FinancialSource() {
		// TODO Auto-generated constructor stub
	}

	public String getUrlEarnings() {
		return urlEarnings;
	}

	public void setUrlEarnings(String urlEarnings) {
		this.urlEarnings = urlEarnings;
	}

	public DataProvider getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(DataProvider siteWeb) {
		this.siteWeb = siteWeb;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}


}
