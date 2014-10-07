package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
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
		jdbcTemplate().execute(
				(ConnectionCallback<Void>) con -> {
					try {
						new Liquibase(dbChangelogPath,
								new ClassLoaderResourceAccessor(),
								DatabaseFactory.getInstance()
										.findCorrectDatabaseImplementation(
												new JdbcConnection(con)))
								.update((Contexts) null);
					} catch (LiquibaseException e) {
						throw new RuntimeException(
								"Couldn't update db using liquibase changest: "
										+ dbChangelogPath, e);
					}
					return null;
				});
	}
}
