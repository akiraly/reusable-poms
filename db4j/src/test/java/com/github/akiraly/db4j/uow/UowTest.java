package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
import com.github.akiraly.db4j.EntityWithLongId;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UowTestConfig.class })
@Nonnull
public class UowTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UowDaoFactory uowDaoFactory;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		transactionTemplate.execute((TransactionStatus status) -> {
			uowDaoFactory.newSchema().create();
			return null;
		});
	}

	@After
	public void tearDown() {
		transactionTemplate.execute((TransactionStatus status) -> {
			uowDaoFactory.newSchema().drop();
			return null;
		});
	}

	@Test(timeout = 5000)
	public void testPersistStandaloneUow() {
		final String uow1User = "u100";
		final Uow uow1 = new Uow(uow1User);

		assertUowCount(0);

		EntityWithLongId<Uow> uow1WithId1 = EntityWithLongId.of(uow1, () -> {
			return transactionTemplate.execute((TransactionStatus status) -> {
				return uowDaoFactory.newDao().persist(uow1);
			});
		});
		Assert.assertSame(uow1, uow1WithId1.getEntity());
		assertUowCount(0);

		Assert.assertTrue(uow1WithId1.getId() > 0);
		assertUowCount(1);

		EntityWithLongId<Uow> uow1WithId2 = uowDaoFactory.newDao().lazyPersist(
				uow1);
		assertUowCount(1);

		Assert.assertSame(uow1, uow1WithId2.getEntity());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Assert.assertTrue(uow1WithId2.getId() > 0);
			}
		});
		assertUowCount(2);

		Assert.assertNotEquals(uow1WithId1.getId(), uow1WithId2.getId());
		uowDaoFactory.newDao().lazyPersist(uow1).getId();

		assertUowCount(2);

		EntityWithLongId<Uow> loadedUow1byId1 = EntityWithLongId.of(() -> {
			return transactionTemplate.execute((TransactionStatus status) -> {
				return uowDaoFactory.newDao().lazyFind(uow1WithId1.getId())
						.getEntity();
			});
		}, uow1WithId1.getId());

		Assert.assertEquals(uow1WithId1.getId(), loadedUow1byId1.getId());
		Assert.assertEquals(uow1User, loadedUow1byId1.getEntity().getUser());

		EntityWithLongId<Uow> loadedUow1byId2 = uowDaoFactory.newDao()
				.lazyFind(uow1WithId2.getId());
		Assert.assertEquals(uow1WithId2.getId(), loadedUow1byId2.getId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Assert.assertEquals(uow1User, loadedUow1byId2.getEntity()
						.getUser());
			}
		});
	}

	private void assertUowCount(long expected) {
		Assert.assertEquals(expected, countUow());
	}

	private long countUow() {
		return jdbcTemplate.queryForObject("select count(*) from uow",
				Long.class);
	}
}

@Configuration
@Import({ UowConfig.class, CommonDbConfig.class })
@Nonnull
class UowTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(
						UowTest.class.getName()
								+ RandomStringUtils.randomAlphabetic(5)
								+ "db;TRACE_LEVEL_FILE=4").build();
	}
}
