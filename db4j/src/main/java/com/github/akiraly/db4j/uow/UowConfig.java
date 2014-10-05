package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Nonnull
public class UowConfig {
	@Bean
	public UowDaoFactory uowDaoFactory(JdbcTemplate jdbcTemplate) {
		return new UowDaoFactory(jdbcTemplate);
	}
}
