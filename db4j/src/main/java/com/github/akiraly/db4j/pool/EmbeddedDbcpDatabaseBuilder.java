package com.github.akiraly.db4j.pool;

import javax.annotation.Nonnull;

@Nonnull
public class EmbeddedDbcpDatabaseBuilder extends
		ConfigurableEmbeddedDatabaseBuilder {

	public EmbeddedDbcpDatabaseBuilder() {
		super(new DbcpDataSourceFactory());
	}
}
