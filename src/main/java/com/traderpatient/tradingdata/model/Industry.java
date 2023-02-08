package com.traderpatient.tradingdata.model;

import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class Industry extends BaseEntity {

	private String industry;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Sector sector;

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Industry() {
		// TODO Auto-generated constructor stub
	}

	public Industry(String pays) {
		super();
		this.industry = pays;
	}

	@Override
	public String toString() {
		return "Industry [industry=" + industry + ", sector=" + sector + "]";
	}
	


}
