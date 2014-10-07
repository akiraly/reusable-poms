package com.github.akiraly.db4j.uow;

import static org.junit.Assert.assertEquals;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.CommonDbConfig;
import com.github.akiraly.db4j.DatabaseLiquibaseInitializer;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FooServiceTestConfig.class })
@Nonnull
public class FooServiceTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private FooDaoFactory fooDaoFactory;

	@Test(timeout = 5000)
	public void testFooService() {
		FooService fooService = new FooService(transactionTemplate,
				fooDaoFactory.newDao());

		fooService.addFoo("bar", 1);
		fooService.assertBar(1, "bar");

		assertEquals(
				1,
				transactionTemplate.execute(
						s -> fooDaoFactory.newDao().deleteAll()).longValue());
	}
}

@Configuration
@Import(CommonDbConfig.class)
@Nonnull
class FooServiceTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(
						FooServiceTest.class.getName()
								+ RandomStringUtils.randomAlphabetic(5)
								+ "db;TRACE_LEVEL_FILE=4").build();
	}

	@Bean
	public FooDaoFactory fooDaoFactory(JdbcTemplate jdbcTemplate) {
		return new FooDaoFactory(jdbcTemplate);
	}

	@Bean
	public DatabaseLiquibaseInitializer databaseLiquibaseInitializer(
			JdbcTemplate jdbcTemplate) {
		return new DatabaseLiquibaseInitializer(jdbcTemplate,
				"com/github/akiraly/db4j/uow/uow_test.xml");
	}
}
