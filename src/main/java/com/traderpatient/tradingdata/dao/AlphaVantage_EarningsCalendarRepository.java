package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.AlphaVantage_EarningsCalendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlphaVantage_EarningsCalendarRepository extends CrudRepository<AlphaVantage_EarningsCalendar, Integer> {
    List<AlphaVantage_EarningsCalendar> findAllByTicker(String ticker);
    List<AlphaVantage_EarningsCalendar> findAllByDate(Date date);
    /**
     * Récupère la 1ère ligne triée par Date du plus récente au plus ancienne
     * @return
     */
    AlphaVantage_EarningsCalendar findTopByOrderByDateDesc();
    AlphaVantage_EarningsCalendar findTopByOrderByFiscalDateEndingDesc();
}
