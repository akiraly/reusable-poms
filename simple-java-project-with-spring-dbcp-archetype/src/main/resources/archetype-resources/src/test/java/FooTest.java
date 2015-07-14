package ${package};

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

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
import com.github.akiraly.db4j.entity.EntityWithLongId;
import com.github.akiraly.db4j.entity.EntityWithStringId;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;

import foo.bar.baz.BarDaoFactory.BarDao;
import foo.bar.baz.FooDaoFactory.FooDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FooTestConfig.class })
@Nonnull
public class FooTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private BarDaoFactory barDaoFactory;

	@Autowired
	private FooDaoFactory fooDaoFactory;

	@Test(timeout = 1500)
	public void testFooDao() {
		List<EntityWithLongId<Foo>> savedEntities = new ArrayList<>();

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				BarDao barDao = barDaoFactory.newDao();
				FooDao fooDao = fooDaoFactory.newDao(barDao);
				assertEquals(0, fooDao.count());

				Bar bar = new Bar("my bar");
				EntityWithStringId<Bar> barWithId = barDao.lazyPersist(bar);

				for (int fi = 0; fi < 5; fi++) {
					Foo entity = new Foo(barWithId, "name " + fi, LocalDateTime
							.now());
					EntityWithLongId<Foo> entityWithId = fooDao
							.lazyPersist(entity);
					assertNotNull(entityWithId.getId());
					assertNotNull(entity.getBar().getId());
					savedEntities.add(entityWithId);
				}

				assertEquals(5, fooDao.count());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				BarDao barDao = barDaoFactory.newDao();
				FooDao fooDao = fooDaoFactory.newDao(barDao);
				assertEquals(5, fooDao.count());

				for (EntityWithLongId<Foo> saved : savedEntities) {
					Optional<Foo> loaded = fooDao.tryFind(saved.getId());
					assertTrue(loaded != null && loaded.isPresent());
					assertFooEquals(saved.getEntity(), loaded.get());
				}
			}

			private void assertFooEquals(Foo expected, Foo actual) {
				assertEquals(expected.getDt(), actual.getDt());
				assertEquals(expected.getName(), actual.getName());
				assertEquals(expected.getBar(), actual.getBar());
				assertEquals(expected.getBar().getEntity().getBarProp(), actual
						.getBar().getEntity().getBarProp());
			}
		});
	}
}

@Configuration
@Import({ FooConfig.class, CommonDbConfig.class })
@Nonnull
class FooTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(FooTest.class.getName() + "db;TRACE_LEVEL_FILE=4")
				.build();
	}

	@Bean
	public DatabaseLiquibaseInitializer databaseLiquibaseInitializer(
			JdbcTemplate jdbcTemplate) {
		return new DatabaseLiquibaseInitializer(jdbcTemplate,
				"foo/bar/baz/foo.xml");
	}
}
