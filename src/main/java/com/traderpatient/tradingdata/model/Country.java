package com.traderpatient.tradingdata.model;


import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class Country extends BaseEntity {

	private String codePays;
	private String pays;

	public Country() {
		super();
	}
	
	public Country(String country) {
		super();
		this.pays = country;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getCodePays() {
		return codePays;
	}

	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}

	@Override
	public String toString() {
		return "Country [pays=" + pays + "]";
	}

}
