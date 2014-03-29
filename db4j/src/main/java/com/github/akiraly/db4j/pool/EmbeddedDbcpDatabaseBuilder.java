package com.github.akiraly.db4j.pool;

import javax.annotation.Nonnull;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Nonnull
public class EmbeddedDbcpDatabaseBuilder extends EmbeddedDatabaseBuilder {

	public EmbeddedDbcpDatabaseBuilder() {
		setDataSourceFactory(new DbcpDataSourceFactory());
	}
}
