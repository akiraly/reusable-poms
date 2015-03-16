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
package com.github.akiraly.db4j.uow;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import com.github.akiraly.db4j.DatabaseSchemaOperation;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FooStringIdServiceTestConfig.class })
@Nonnull
public class FooStringIdServiceTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private FooStringIdDaoFactory fooDaoFactory;

	@Test(timeout = 5000)
	public void testFooService() {
		FooStringIdService fooService = new FooStringIdService(
				transactionTemplate, fooDaoFactory.newDao());

		String fooId = fooService.addFoo("bar");
		fooService.assertBar(fooId, "bar");

		assertEquals(
				1,
				transactionTemplate.execute(
						s -> fooDaoFactory.newDao().deleteAll()).longValue());
	}
}

@Configuration
@Import(CommonDbConfig.class)
@Nonnull
class FooStringIdServiceTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(
						FooStringIdServiceTest.class.getName()
								+ RandomStringUtils.randomAlphabetic(5)
								+ "db;TRACE_LEVEL_FILE=4").build();
	}

	@Bean
	public FooStringIdDaoFactory fooDaoFactory(JdbcTemplate jdbcTemplate,
			List<DatabaseSchemaOperation> schemaOps) {
		return new FooStringIdDaoFactory(jdbcTemplate);
	}

	@Bean
	public DatabaseLiquibaseInitializer databaseLiquibaseInitializer(
			JdbcTemplate jdbcTemplate) {
		return new DatabaseLiquibaseInitializer(jdbcTemplate,
				"com/github/akiraly/db4j/uow/uow_test.xml");
	}
}
