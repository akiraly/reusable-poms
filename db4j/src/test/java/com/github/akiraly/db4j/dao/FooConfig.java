package com.github.akiraly.db4j.dao;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.akiraly.db4j.DatabaseSchemaOperation;

@Configuration
@Nonnull
public class FooConfig {
	@Bean
	public BarDaoFactory barDaoFactory(JdbcTemplate jdbcTemplate,
			List<DatabaseSchemaOperation> schemaOps) {
		return new BarDaoFactory(jdbcTemplate);
	}

	@Bean
	public FooDaoFactory fooDaoFactory(JdbcTemplate jdbcTemplate,
			List<DatabaseSchemaOperation> schemaOps) {
		return new FooDaoFactory(jdbcTemplate);
	}
}