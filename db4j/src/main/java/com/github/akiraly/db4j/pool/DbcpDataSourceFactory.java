package com.github.akiraly.db4j.pool;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.sql.Driver;

import javax.annotation.Nonnull;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

@Nonnull
public class DbcpDataSourceFactory implements DataSourceFactory,
		ConnectionProperties {
	private final BasicDataSource dataSource;

	public DbcpDataSourceFactory() {
		this(DbcpUtils.newDefaultDS());
	}

	public DbcpDataSourceFactory(BasicDataSource dataSource) {
		this.dataSource = argNotNull(dataSource, "dataSource");
	}

	@Override
	public ConnectionProperties getConnectionProperties() {
		return this;
	}

	@Override
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	@Override
	public void setUsername(String username) {
		dataSource.setUsername(username);
	}

	@Override
	public void setPassword(String password) {
		dataSource.setPassword(password);
	}

	@Override
	public void setUrl(String url) {
		dataSource.setUrl(url);
	}

	@Override
	public void setDriverClass(Class<? extends Driver> driverClass) {
		dataSource.setDriverClassName(driverClass.getName());
	}
}
