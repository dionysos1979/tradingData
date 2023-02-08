package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.IndiceTicker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndiceTickerRepository extends CrudRepository<IndiceTicker, Integer> {

}
