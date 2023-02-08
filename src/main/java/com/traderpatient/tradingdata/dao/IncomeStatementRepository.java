package com.traderpatient.tradingdata.dao;


import com.traderpatient.tradingdata.model.incomeStatement.IncomeStatement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeStatementRepository extends CrudRepository<IncomeStatement, Integer> {
    List<IncomeStatement> findAllByTicker(String ticker);
    List<IncomeStatement> findAllByTickerAndFrequence(String ticker, String frequence);
    IncomeStatement findTopFiscalDateEndingByTickerAndFrequence(String ticker, String frequence);
}
