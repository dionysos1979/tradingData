package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.Indice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IndiceRepository extends CrudRepository<Indice, Integer> {
    Indice findByTicker(String ticker);
}
