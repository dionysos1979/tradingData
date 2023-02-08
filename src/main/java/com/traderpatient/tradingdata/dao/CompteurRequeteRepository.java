package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.CompteurRequete;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CompteurRequeteRepository extends CrudRepository<CompteurRequete, Integer> {
    CompteurRequete findBySiteWebAndDateAndApikey(String siteWeb, Date date, String apikey);
}
