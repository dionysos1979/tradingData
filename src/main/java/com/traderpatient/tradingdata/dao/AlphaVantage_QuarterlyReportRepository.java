package com.traderpatient.tradingdata.dao;


import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlphaVantage_QuarterlyReportRepository extends CrudRepository<AlphaVantage_QuarterlyReport, Integer> {
    List<AlphaVantage_QuarterlyReport> findAllByTicker(String ticker);
    AlphaVantage_QuarterlyReport findByTickerOrderByDate(String ticker);
}
