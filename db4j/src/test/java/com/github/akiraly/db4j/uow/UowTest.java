package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.CommonDbConfig;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UowConfig.class, UowTest.Config.class })
@Nonnull
public class UowTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private TestCaseHandlerFactory testCaseHandlerFactory;

	@Test(timeout = 8000)
	public void testPersistStandaloneUow() {
		final String uow1User = "u100";
		final Uow uow1 = new Uow(uow1User);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory.get();
				handler.uowDao.persist(uow1);
				Assert.assertNotNull(uow1.getId());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory.get();
				Optional<Uow> uow1Loaded = handler.uowDao.find(uow1.getId());
				assertPresent(uow1Loaded);
				Assert.assertEquals(uow1User, uow1Loaded.get().getUser());
				Assert.assertEquals(uow1, uow1Loaded.get());

			}
		});
	}

	@Test(timeout = 8000)
	public void testCascadePersistenceOnUow() {
		final String uow1User = "u200";
		final Uow uow1 = new Uow(uow1User);
		final AuditedFoo auditedFoo = new AuditedFoo(uow1);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory.get();
				handler.auditedFooDao.persist(auditedFoo);
				Assert.assertNotNull(auditedFoo.getId());
				Assert.assertNotNull(uow1.getId());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory.get();
				Optional<AuditedFoo> fooLoaded = handler.auditedFooDao
						.find(auditedFoo.getId());
				assertPresent(fooLoaded);
				Assert.assertNotNull(fooLoaded.get().getCreateUow());
				Assert.assertSame(fooLoaded.get().getCreateUow(), fooLoaded
						.get().getUpdateUow());

				Optional<Uow> uow1Loaded = handler.uowDao.find(uow1.getId());
				assertPresent(uow1Loaded);
				Assert.assertEquals(uow1User, uow1Loaded.get().getUser());
				Assert.assertSame(fooLoaded.get().getCreateUow(),
						uow1Loaded.get());
			}
		});

		final String uow2User = "u250";
		final Uow uow2 = new Uow(uow2User);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory.get();
				Optional<AuditedFoo> fooLoaded = handler.auditedFooDao
						.find(auditedFoo.getId());
				fooLoaded.get().setBar("bar");
				fooLoaded.get().setUpdateUow(uow2);
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory.get();
				Optional<AuditedFoo> fooLoaded = handler.auditedFooDao
						.find(auditedFoo.getId());
				assertPresent(fooLoaded);
				Assert.assertNotNull(fooLoaded.get().getCreateUow());
				Assert.assertNotSame(fooLoaded.get().getCreateUow(), fooLoaded
						.get().getUpdateUow());
				Assert.assertEquals(uow1User, fooLoaded.get().getCreateUow()
						.getUser());
				Assert.assertEquals(uow1, fooLoaded.get().getCreateUow());
				Assert.assertEquals(uow2User, fooLoaded.get().getUpdateUow()
						.getUser());
				Assert.assertEquals(uow2, fooLoaded.get().getUpdateUow());
			}
		});
	}

	private static void assertPresent(Optional<?> o) {
		Assert.assertNotNull(o);
		Assert.assertTrue(o.isPresent());
	}

	@Configuration
	@Nonnull
	static class Config extends CommonDbConfig {
		@Bean
		public DataSource dataSource() {
			org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
			dataSource.setDefaultAutoCommit(false);
			dataSource.setValidationQuery("SELECT 1");
			dataSource.setDataSource(new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName(UowTest.class.getName() + "db").build());

			return dataSource;
		}

		@Bean
		public Database dbType() {
			return Database.H2;
		}

		@Override
		protected boolean showSql() {
			return true;
		}

		@Bean
		public Set<Package> packagesToScan() {
			return ImmutableSet.of(AuditedFoo.class.getPackage());
		}

		@Bean
		public AuditedFooDaoFactory auditedFooDaoFactory(
				EntityManagerFactory entityManagerFactory) {
			return new AuditedFooDaoFactory(entityManagerFactory);
		}

		@Bean
		public TestCaseHandlerFactory testCaseHandlerFactory(
				UowDaoFactory uowDaoFactory,
				AuditedFooDaoFactory auditedFooDaoFactory) {
			return new TestCaseHandlerFactory(auditedFooDaoFactory,
					uowDaoFactory);
		}
	}

	@Nonnull
	static class TestCaseHandlerFactory implements Supplier<TestCaseHandler> {
		private final AuditedFooDaoFactory auditedFooDaoFactory;
		private final UowDaoFactory uowDaoFactory;

		public TestCaseHandlerFactory(
				AuditedFooDaoFactory auditedFooDaoFactory,
				UowDaoFactory uowDaoFactory) {
			this.auditedFooDaoFactory = argNotNull(auditedFooDaoFactory,
					"auditedFooDaoFactory");
			this.uowDaoFactory = argNotNull(uowDaoFactory, "uowDaoFactory");
		}

		@Override
		public TestCaseHandler get() {
			return new TestCaseHandler(auditedFooDaoFactory.get(),
					uowDaoFactory.get());
		}
	}

	@Nonnull
	private static class TestCaseHandler {
		private final AuditedFooDao auditedFooDao;

		private final UowDao uowDao;

		public TestCaseHandler(AuditedFooDao auditedFooDao, UowDao uowDao) {
			this.auditedFooDao = argNotNull(auditedFooDao, "auditedFooDao");
			this.uowDao = argNotNull(uowDao, "uowDao");
		}
	}
}
