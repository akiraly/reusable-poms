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
import org.junit.Assert;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.CommonDbConfig;
import com.github.akiraly.db4j.DatabaseLiquibaseInitializer;
import com.github.akiraly.db4j.DatabaseSchemaOperation;
import com.github.akiraly.db4j.EntityWithLongId;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;
import com.github.akiraly.db4j.uow.AuditedFooDaoFactory.AuditedFooDao;
import com.github.akiraly.db4j.uow.UowDaoFactory.UowDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UowCascadePersistenceTestConfig.class })
@Nonnull
public class UowCascadePersistenceTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AuditedFooDaoFactory auditedFooDaoFactory;

	@Autowired
	private UowDaoFactory uowDaoFactory;

	@Test(timeout = 5000)
	public void testCascadePersistenceOnUow() {
		final String uow1User = "u200";
		final Uow uow1 = new Uow(uow1User);
		EntityWithLongId<Uow> uowWithId1 = uowDaoFactory.newDao().lazyPersist(
				uow1);
		AuditedFoo auditedFoo = new AuditedFoo("bar", uowWithId1);
		EntityWithLongId<AuditedFoo> auditedFooWithId = transactionTemplate
				.execute(s -> {
					AuditedFooDao auditedFooDao = auditedFooDaoFactory
							.newDao(uowDaoFactory.newDao());
					Assert.assertEquals(0, auditedFooDao.count());
					EntityWithLongId<AuditedFoo> result = auditedFooDao
							.lazyPersist(auditedFoo);
					Assert.assertEquals(1, result.getId());
					Assert.assertEquals(1, uowWithId1.getId());
					Assert.assertEquals(1, auditedFooDao.count());
					return result;
				});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				UowDao uowDao = uowDaoFactory.newDao();
				AuditedFooDao auditedFooDao = auditedFooDaoFactory
						.newDao(uowDao);
				Assert.assertEquals(1, auditedFooDao.count());
				AuditedFoo fooLoaded = auditedFooDao.lazyFind(
						auditedFooWithId.getId()).getEntity();
				Assert.assertNotNull(fooLoaded.getCreateUow().getEntity());
				Assert.assertEquals(fooLoaded.getCreateUow(),
						fooLoaded.getUpdateUow());
				// TODO: make this work again - caching:
				// Assert.assertSame(fooLoaded.getCreateUow().getEntity(),
				// fooLoaded.getUpdateUow().getEntity());

				EntityWithLongId<Uow> uow1Loaded = uowDao.lazyFind(uowWithId1
						.getId());
				Assert.assertEquals(uow1User, uow1Loaded.getEntity().getUser());
				Assert.assertEquals(fooLoaded.getCreateUow(), uow1Loaded);
				// TODO: make this work again - caching:
				// Assert.assertSame(fooLoaded.getCreateUow().getEntity(),
				// uow1Loaded.getEntity());
			}
		});

		final String uow2User = "u250";
		Uow uow2 = new Uow(uow2User);
		EntityWithLongId<Uow> uowWithId2 = uowDaoFactory.newDao().lazyPersist(
				uow2);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				AuditedFooDao auditedFooDao = auditedFooDaoFactory
						.newDao(uowDaoFactory.newDao());
				EntityWithLongId<AuditedFoo> fooLoaded = auditedFooDao
						.lazyFind(auditedFooWithId.getId());
				assertEquals("bar", fooLoaded.getEntity().getBar());
				AuditedFoo auditedFoo2 = fooLoaded.getEntity().updateBar(
						"newBar", uowWithId2);
				auditedFooDao.update(auditedFooWithId.getId(), auditedFoo2);
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				AuditedFooDao auditedFooDao = auditedFooDaoFactory
						.newDao(uowDaoFactory.newDao());
				EntityWithLongId<AuditedFoo> fooWithIdLoaded = auditedFooDao
						.lazyFind(auditedFooWithId.getId());
				AuditedFoo fooLoaded = fooWithIdLoaded.getEntity();
				Assert.assertNotNull(fooLoaded.getCreateUow());
				Assert.assertNotSame(fooLoaded.getCreateUow(),
						fooLoaded.getUpdateUow());
				Assert.assertNotEquals(fooLoaded.getCreateUow(),
						fooLoaded.getUpdateUow());
				Assert.assertEquals(uow1User, fooLoaded.getCreateUow()
						.getEntity().getUser());
				Assert.assertEquals(uowWithId1, fooLoaded.getCreateUow());
				Assert.assertEquals(uow2User, fooLoaded.getUpdateUow()
						.getEntity().getUser());
				Assert.assertEquals(uowWithId2, fooLoaded.getUpdateUow());
				assertEquals("newBar", fooLoaded.getBar());
			}
		});
	}
}

@Configuration
@Import({ UowConfig.class, CommonDbConfig.class })
@Nonnull
class UowCascadePersistenceTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(
						UowCascadePersistenceTest.class.getName()
								+ RandomStringUtils.randomAlphabetic(5)
								+ "db;TRACE_LEVEL_FILE=4").build();
	}

	@Bean
	public AuditedFooDaoFactory auditedFooDaoFactory(JdbcTemplate jdbcTemplate,
			List<DatabaseSchemaOperation> schemaOps) {
		return new AuditedFooDaoFactory(jdbcTemplate);
	}

	@Bean
	public DatabaseLiquibaseInitializer databaseLiquibaseInitializer(
			JdbcTemplate jdbcTemplate) {
		return new DatabaseLiquibaseInitializer(jdbcTemplate,
				"com/github/akiraly/db4j/uow/uow_test.xml");
	}
}
