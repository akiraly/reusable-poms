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
		InitializingBean {
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
