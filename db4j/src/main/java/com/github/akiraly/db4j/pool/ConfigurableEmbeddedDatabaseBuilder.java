package com.github.akiraly.db4j.pool;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.google.common.base.Optional;

/**
 * Modeled after {@link EmbeddedDatabaseBuilder}. Enables setting a custom
 * {@link DataSourceFactory}.
 */
@Nonnull
public class ConfigurableEmbeddedDatabaseBuilder {
	private final EmbeddedDatabaseFactory databaseFactory;

	private final ResourceDatabasePopulator databasePopulator;

	private final ResourceLoader resourceLoader;

	public ConfigurableEmbeddedDatabaseBuilder() {
		this(new DefaultResourceLoader());
	}

	public ConfigurableEmbeddedDatabaseBuilder(ResourceLoader resourceLoader) {
		this(resourceLoader, Optional.<DataSourceFactory> absent());
	}

	public ConfigurableEmbeddedDatabaseBuilder(
			DataSourceFactory dataSourceFactory) {
		this(new DefaultResourceLoader(), dataSourceFactory);
	}

	public ConfigurableEmbeddedDatabaseBuilder(ResourceLoader resourceLoader,
			DataSourceFactory dataSourceFactory) {
		this(resourceLoader, Optional.of(dataSourceFactory));
	}

	public ConfigurableEmbeddedDatabaseBuilder(ResourceLoader resourceLoader,
			Optional<DataSourceFactory> dataSourceFactory) {
		databaseFactory = new EmbeddedDatabaseFactory();
		if (dataSourceFactory.isPresent())
			databaseFactory.setDataSourceFactory(dataSourceFactory.get());
		databasePopulator = new ResourceDatabasePopulator();
		databaseFactory.setDatabasePopulator(databasePopulator);
		this.resourceLoader = argNotNull(resourceLoader, "resourceLoader");
	}

	public ConfigurableEmbeddedDatabaseBuilder setName(String databaseName) {
		databaseFactory
				.setDatabaseName(argNotNull(databaseName, "databaseName"));
		return this;
	}

	public ConfigurableEmbeddedDatabaseBuilder setType(
			EmbeddedDatabaseType databaseType) {
		databaseFactory
				.setDatabaseType(argNotNull(databaseType, "databaseType"));
		return this;
	}

	public ConfigurableEmbeddedDatabaseBuilder addScript(String sqlResource) {
		databasePopulator.addScript(resourceLoader.getResource(argNotNull(
				sqlResource, "sqlResource")));
		return this;
	}

	public EmbeddedDatabase build() {
		return databaseFactory.getDatabase();
	}
}
