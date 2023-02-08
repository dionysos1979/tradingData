package com.traderpatient.tradingdata.dao;

import com.traderpatient.tradingdata.model.DataProvider;
import com.traderpatient.tradingdata.model.Indice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataProviderRepository extends CrudRepository<DataProvider, Integer> {

}
