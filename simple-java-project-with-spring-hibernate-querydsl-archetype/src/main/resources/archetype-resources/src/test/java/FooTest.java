package $package;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import foo.bar.baz.entity.Bar;
import foo.bar.baz.entity.Foo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CommonAppConfig.class, AppTest.Config.class })
@Nonnull
public class FooTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private FooDaoFactory fooDaoFactory;

	@Test(timeout = 5000)
	public void testEntityManagerFactory() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				FooDao fooDao = fooDaoFactory.get(new Bar());

				for (int fi = 0; fi < 5; fi++) {
					Foo entity = new Foo();
					entity = fooDao.persist(entity);
					LOGGER.debug("Saved foo = {}", entity);
				}
			}
		});
	}

	@Configuration
	@Nonnull
	static class Config {
		@Bean
		public DataSource dataSource() {
			org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
			dataSource.setDefaultAutoCommit(false);
			dataSource.setValidationQuery("SELECT 1");
			dataSource.setDataSource(new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2).setName("fooDb").build());

			return dataSource;
		}

		@Bean
		public Database dbType() {
			return Database.H2;
		}
	}
}
