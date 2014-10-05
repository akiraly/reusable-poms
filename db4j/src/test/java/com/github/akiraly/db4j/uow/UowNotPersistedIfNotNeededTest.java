package com.github.akiraly.db4j.uow;

import static org.junit.Assert.assertEquals;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
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
import com.github.akiraly.db4j.uow.AuditedFooDaoFactory.AuditedFooDao;
import com.github.akiraly.db4j.uow.FooDaoFactory.FooDao;
import com.github.akiraly.db4j.uow.UowDaoFactory.UowDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UowNotPersistedIfNotNeededTestConfig.class })
@Nonnull
public class UowNotPersistedIfNotNeededTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private FooDaoFactory fooDaoFactory;

	@Autowired
	private AuditedFooDaoFactory auditedFooDaoFactory;

	@Autowired
	private UowDaoFactory uowDaoFactory;

	@Before
	public void setUp() {
		transactionTemplate.execute((TransactionStatus status) -> {
			uowDaoFactory.newSchema().create();
			fooDaoFactory.newSchema().create();
			auditedFooDaoFactory.newSchema().create();
			return null;
		});
	}

	@After
	public void tearDown() {
		transactionTemplate.execute((TransactionStatus status) -> {
			auditedFooDaoFactory.newSchema().drop();
			fooDaoFactory.newSchema().drop();
			uowDaoFactory.newSchema().drop();
			return null;
		});
	}

	@Test(timeout = 5000)
	public void testUowNotPersistedIfNotNeeded() {
		final String uow1User = "u300";
		Uow uow1 = new Uow(uow1User);

		final Foo foo = new Foo("bar");

		EntityWithLongId<Foo> fooWithId = transactionTemplate.execute(s -> {
			FooDao fooDao = fooDaoFactory.newDao();
			UowDao uowDao = uowDaoFactory.newDao();
			assertEquals(0, fooDao.count());
			assertEquals(0, uowDao.count());
			EntityWithLongId<Foo> result = fooDao.lazyPersist(foo);
			assertEquals(1, result.getId());
			assertEquals(1, fooDao.count());
			assertEquals(0, uowDao.count());
			return result;
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				FooDao fooDao = fooDaoFactory.newDao();
				assertEquals(1, fooDao.count());
				assertEquals("bar", fooDao.lazyFind(fooWithId.getId())
						.getEntity().getBar());
				assertEquals(1, fooDao.deleteAll());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				UowDao uowDao = uowDaoFactory.newDao();
				AuditedFooDao auditedFooDao = auditedFooDaoFactory
						.newDao(uowDao);
				assertEquals(0, uowDao.count());
				EntityWithLongId<Uow> uowWithId1 = uowDao.lazyPersist(uow1);
				AuditedFoo auditedFoo = new AuditedFoo("bar", uowWithId1);
				EntityWithLongId<AuditedFoo> fooWithId = auditedFooDao
						.lazyPersist(auditedFoo);
				assertEquals("bar", fooWithId.getEntity().getBar());
				assertEquals(0, uowDao.count());
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
	public FooDaoFactory fooDaoFactory(JdbcTemplate jdbcTemplate) {
		return new FooDaoFactory(jdbcTemplate);
	}

	@Bean
	public AuditedFooDaoFactory auditedFooDaoFactory(JdbcTemplate jdbcTemplate) {
		return new AuditedFooDaoFactory(jdbcTemplate);
	}
}
