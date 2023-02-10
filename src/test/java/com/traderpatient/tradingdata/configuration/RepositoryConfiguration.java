package com.traderpatient.tradingdata.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.traderpatient.tradingdata.model"})
@EnableJpaRepositories(basePackages = {"com.traderpatient.tradingdata.dao"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
