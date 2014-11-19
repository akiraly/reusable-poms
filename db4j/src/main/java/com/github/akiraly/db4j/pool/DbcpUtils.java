/**
 * Copyright 2014 Attila Kiraly
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
