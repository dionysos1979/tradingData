package com.traderpatient.tradingdata.dao;


import com.traderpatient.tradingdata.model.balanceSheet.BalanceSheet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceSheetRepository extends CrudRepository<BalanceSheet, Integer> {
    List<BalanceSheet> findAllByTicker(String ticker);
    List<BalanceSheet> findAllByTickerAndFrequence(String ticker, String frequence);
    BalanceSheet findTopFiscalDateEndingByTickerAndFrequence(String ticker, String frequence);
}
