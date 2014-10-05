package com.github.akiraly.db4j;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@Nonnull
public class CommonDbConfig {
	@Bean
	public TransactionTemplate transactionTemplate(
			PlatformTransactionManager transactionManager) {
		DefaultTransactionDefinition td = new DefaultTransactionDefinition();
		td.setTimeout(30);
		return new TransactionTemplate(transactionManager, td);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource, false);
	}
}