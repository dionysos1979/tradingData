package com.traderpatient.tradingdata.model;

import com.traderpatient.tradingdata.model.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
@MappedSuperclass
public class Sector  extends BaseEntity {

	private String sector;
	
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private Set<Industry> indutries = new HashSet<>();


	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Sector() {
		// TODO Auto-generated constructor stub
	}

	
	public Sector(String sector) {
		super();
		this.sector = sector;
	}

	@Override
	public String toString() {
		return "Sector [sector=" + sector + "]";
	}


}
