package com.traderpatient.tradingdata.dao;


import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Polygon_DailyQuoteRepository extends CrudRepository<Polygon_DailyQuote, Integer> {
    List<Polygon_DailyQuote> findAllByDate(Date date);
    Polygon_DailyQuote findByDate(Date date);
}
