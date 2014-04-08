package com.github.akiraly.db4j.uow;

import java.util.Set;

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
@ContextConfiguration(classes = { UowTestConfig.class })
@Nonnull
public class UowTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UowDaoFactory uowDaoFactory;

	@Test(timeout = 16000)
	public void testPersistStandaloneUow() {
		final String uow1User = "u100";
		final Uow uow1 = new Uow(uow1User);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				uowDaoFactory.get().persist(uow1);
				Assert.assertNotNull(uow1.getId());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Uow uow1Loaded = uowDaoFactory.get().find(uow1.getId());
				Assert.assertEquals(uow1User, uow1Loaded.getUser());
				Assert.assertEquals(uow1, uow1Loaded);
			}
		});
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

	@Bean
	public Database dbType() {
		return Database.H2;
	}

	@Bean
	public Set<Package> packagesToScan() {
		return ImmutableSet.of(Uow.class.getPackage());
	}
}
