package com.github.akiraly.db4j.uow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.CommonDbConfig;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UowNotPersistedIfNotNeededTestConfig.class })
@Nonnull
public class UowNotPersistedIfNotNeededTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private FooDaoFactory fooDaoFactory;

	@Test(timeout = 16000)
	public void testUowNotPersistedIfNotNeeded() {
		final String uow1User = "u300";
		final Uow uow1 = new Uow(uow1User);
		final Foo foo = new Foo();

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			final FooDao fooDao = fooDaoFactory.get();

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				assertEquals(0, fooDao.count());
				fooDao.persist(foo);
				assertNotNull(foo.getId());
				assertNull(uow1.getId());
				assertEquals(1, fooDao.count());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				assertEquals(1, fooDaoFactory.get().count());
				assertEquals(1, fooDaoFactory.get().listAll().size());
				fooDaoFactory.get().find(foo.getId());
				assertEquals(1, fooDaoFactory.get().deleteAll());
			}
		});
	}
}

@Configuration
@Import({ UowConfig.class, CommonDbConfig.class })
@Nonnull
class UowNotPersistedIfNotNeededTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(
						UowNotPersistedIfNotNeededTest.class.getName()
								+ RandomStringUtils.randomAlphabetic(5)
								+ "db;TRACE_LEVEL_FILE=4").build();
	}

	@Bean
	public Database dbType() {
		return Database.H2;
	}

	@Bean
	public Set<Package> packagesToScan() {
		return ImmutableSet.of(AuditedFoo.class.getPackage());
	}

	@Bean
	public FooDaoFactory fooDaoFactory(EntityManagerFactory entityManagerFactory) {
		return new FooDaoFactory(entityManagerFactory);
	}
}
