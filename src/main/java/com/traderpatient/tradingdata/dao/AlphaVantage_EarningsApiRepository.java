package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.AlphaVantage_EarningsApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlphaVantage_EarningsApiRepository extends CrudRepository<AlphaVantage_EarningsApi, Integer> {
   // List<AlphaVantage_EarningsApi> findAllByTicker(String ticker);
    //AlphaVantage_EarningsApi findByTicker(String ticker);
    //AlphaVantage_EarningsApi findTopDateByTicker(String ticker);
}
