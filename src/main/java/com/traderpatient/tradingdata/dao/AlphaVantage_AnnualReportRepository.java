package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_AnnualReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlphaVantage_AnnualReportRepository extends CrudRepository<AlphaVantage_AnnualReport, Integer> {
    List<AlphaVantage_AnnualReport> findAllByTicker(String ticker);
}
