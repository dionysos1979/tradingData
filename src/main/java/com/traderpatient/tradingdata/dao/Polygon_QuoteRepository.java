package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.Polygon_Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Polygon_QuoteRepository extends CrudRepository<Polygon_Quote, Integer> {
    List<Polygon_Quote> findAllByCotationId(Integer cotationId);
    List<Polygon_Quote> findAllByTicker(String ticker);
    List<Polygon_Quote> findAllByTickerOrderByDateDesc(String ticker);
}
