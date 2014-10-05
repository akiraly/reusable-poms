package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import org.springframework.jdbc.core.JdbcTemplate;

@Nonnull
public abstract class JdbcTemplateAware {
	private final JdbcTemplate jdbcTemplate;

	protected JdbcTemplateAware(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = argNotNull(jdbcTemplate, "jdbcTemplate");
	}

	protected final JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}
}
