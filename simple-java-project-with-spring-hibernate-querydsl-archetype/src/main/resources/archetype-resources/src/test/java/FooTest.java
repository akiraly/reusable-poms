package ${package};

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.base.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FooTestConfig.class })
@Nonnull
public class FooTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(FooTest.class);

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private FooDaoFactory fooDaoFactory;

	@Test(timeout = 4000)
	public void testFooDao() {
		final List<Foo> savedEntities = newArrayList();

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				FooDao fooDao = fooDaoFactory.get();
				Assert.assertEquals(0, fooDao.count());

				Bar bar = new Bar();
				Assert.assertNull(bar.getId());

				for (int fi = 0; fi < 5; fi++) {
					Foo entity = new Foo();
					entity.setBar(bar);
					Assert.assertNull(entity.getId());
					fooDao.persist(entity);
					LOGGER.debug("Saved foo = {}", entity);
					Assert.assertNotNull(entity.getId());
					Assert.assertNotNull(entity.getBar().getId());
					savedEntities.add(entity);
				}

				Assert.assertEquals(5, fooDao.count());
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				FooDao fooDao = fooDaoFactory.get();
				Assert.assertEquals(5, fooDao.count());

				for (Foo saved : savedEntities) {
					Optional<Foo> loaded = fooDao.tryFind(saved.getId());
					Assert.assertTrue(loaded != null && loaded.isPresent());
					Assert.assertEquals(saved, loaded.get());
					Assert.assertEquals(saved.getBar(), loaded.get().getBar());
				}
			}
		});
	}
}
