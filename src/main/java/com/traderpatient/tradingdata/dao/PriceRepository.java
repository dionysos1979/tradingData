package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<Price, Integer> {
    List<Price> findAllByDate(Date date);
    List<Price> findAllByTickerOrderByDateDesc(String ticker);
}
