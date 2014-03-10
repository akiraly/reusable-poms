package com.github.akiraly.db4j.pool;

import java.sql.Connection;

import javax.annotation.Nonnull;

import org.apache.commons.dbcp2.BasicDataSource;

@Nonnull
public class DbcpUtils {
	public static BasicDataSource newDefaultDS() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDefaultAutoCommit(false);

		dataSource.setDefaultQueryTimeout(1);
		dataSource.setValidationQueryTimeout(1);
		dataSource.setMaxWaitMillis(5000);

		dataSource
				.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

		dataSource.setInitialSize(4);
		dataSource.setMinIdle(4);
		dataSource.setMaxIdle(8);
		dataSource.setMaxTotal(16);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(128);
		return dataSource;
	}
}
