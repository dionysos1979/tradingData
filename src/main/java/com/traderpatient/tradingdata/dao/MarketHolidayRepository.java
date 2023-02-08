package com.traderpatient.tradingdata.dao;


import com.traderpatient.tradingdata.model.MarketHoliday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketHolidayRepository extends CrudRepository<MarketHoliday, Integer> {

}
