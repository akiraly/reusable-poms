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
package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.sql.Connection;

import javax.annotation.Nonnull;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

@Nonnull
public class DatabaseLiquibaseInitializer extends JdbcTemplateAware implements
		InitializingBean, DatabaseSchemaOperation {
	private final String dbChangelogPath;

	public DatabaseLiquibaseInitializer(JdbcTemplate jdbcTemplate,
			String dbChangelogPath) {
		super(jdbcTemplate);
		this.dbChangelogPath = argNotNull(dbChangelogPath, "dbChangelogPath");
	}

	@Override
	public void afterPropertiesSet() {
		jdbcTemplate().execute((ConnectionCallback<Void>) con -> {
			doDbUpdate(con);
			return null;
		});
	}

	private void doDbUpdate(Connection con) {
		try {
			newLiquibase(con).update((Contexts) null);
		} catch (LiquibaseException e) {
			throw new RuntimeException(
					"Couldn't update db using liquibase changset: "
							+ dbChangelogPath, e);
		}
	}

	private Liquibase newLiquibase(Connection con) throws LiquibaseException,
			DatabaseException {
		return new Liquibase(dbChangelogPath,
				new ClassLoaderResourceAccessor(), newLiquibaseDb(con));
	}

	private Database newLiquibaseDb(Connection con) throws DatabaseException {
		return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
				new JdbcConnection(con));
	}
}
