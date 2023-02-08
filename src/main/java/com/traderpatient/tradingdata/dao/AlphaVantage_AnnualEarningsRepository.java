package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.AlphaVantage_AnnualEarnings;
import com.traderpatient.tradingdata.model.AlphaVantage_QuarterlyEarnings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlphaVantage_AnnualEarningsRepository extends CrudRepository<AlphaVantage_AnnualEarnings, Integer> {
    List<AlphaVantage_AnnualEarnings> findAllByTicker(String ticker);
    List<AlphaVantage_QuarterlyEarnings> findTopDateByTicker(String ticker);

}
