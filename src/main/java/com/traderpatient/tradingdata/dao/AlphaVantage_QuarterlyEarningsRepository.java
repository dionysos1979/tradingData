package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.AlphaVantage_QuarterlyEarnings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlphaVantage_QuarterlyEarningsRepository extends CrudRepository<AlphaVantage_QuarterlyEarnings, Integer> {
    List<AlphaVantage_QuarterlyEarnings> findAllByTicker(String ticker);
    List<AlphaVantage_QuarterlyEarnings> findTopDateByTicker(String ticker);

}
