package com.github.akiraly.db4j.uow;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
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
@ContextConfiguration(classes = { UowCascadePersistenceTestConfig.class })
@Nonnull
public class UowCascadePersistenceTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AuditedFooDaoFactory auditedFooDaoFactory;

	@Autowired
	private UowDaoFactory uowDaoFactory;

	@Test(timeout = 16000)
	public void testCascadePersistenceOnUow() {
		final String uow1User = "u200";
		final Uow uow1 = new Uow(uow1User);
		final AuditedFoo auditedFoo = new AuditedFoo(uow1);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Assert.assertEquals(0, auditedFooDaoFactory.get().count());
				auditedFooDaoFactory.get().persist(auditedFoo);
				Assert.assertNotNull(auditedFoo.getId());
				Assert.assertNotNull(uow1.getId());
				Assert.assertEquals(1, auditedFooDaoFactory.get().count());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Assert.assertEquals(1, auditedFooDaoFactory.get().count());
				AuditedFoo fooLoaded = auditedFooDaoFactory.get().find(
						auditedFoo.getId());
				Assert.assertNotNull(fooLoaded.getCreateUow());
				Assert.assertSame(fooLoaded.getCreateUow(),
						fooLoaded.getUpdateUow());

				Uow uow1Loaded = uowDaoFactory.get().find(uow1.getId());
				Assert.assertEquals(uow1User, uow1Loaded.getUser());
				Assert.assertSame(fooLoaded.getCreateUow(), uow1Loaded);
			}
		});

		final String uow2User = "u250";
		final Uow uow2 = new Uow(uow2User);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				AuditedFoo fooLoaded = auditedFooDaoFactory.get().find(
						auditedFoo.getId());
				fooLoaded.setBar("bar");
				fooLoaded.setUpdateUow(uow2);
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				AuditedFoo fooLoaded = auditedFooDaoFactory.get().find(
						auditedFoo.getId());
				Assert.assertNotNull(fooLoaded.getCreateUow());
				Assert.assertNotSame(fooLoaded.getCreateUow(),
						fooLoaded.getUpdateUow());
				Assert.assertEquals(uow1User, fooLoaded.getCreateUow()
						.getUser());
				Assert.assertEquals(uow1, fooLoaded.getCreateUow());
				Assert.assertEquals(uow2User, fooLoaded.getUpdateUow()
						.getUser());
				Assert.assertEquals(uow2, fooLoaded.getUpdateUow());
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
	public Database dbType() {
		return Database.H2;
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
}
