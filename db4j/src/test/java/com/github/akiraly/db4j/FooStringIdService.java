package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.annotation.Nonnull;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.TransactionTemplateAware;
import com.github.akiraly.db4j.FooStringIdDaoFactory.FooStringIdDao;
import com.github.akiraly.db4j.entity.EntityWithStringId;

@Nonnull
public class FooStringIdService extends TransactionTemplateAware {
	private final FooStringIdDao fooDao;

	public FooStringIdService(TransactionTemplate transactionTemplate,
			FooStringIdDao fooDao) {
		super(transactionTemplate);
		this.fooDao = argNotNull(fooDao, "fooDao");
	}

	public String addFoo(String bar) {
		return tx(status -> {
			EntityWithStringId<Foo> entity = fooDao.lazyPersist(new Foo(bar,
					LocalDateTime.now(ZoneOffset.UTC)));
			assertNotNull(entity.getId());
			assertNotNull(entity.getEntity().getBar());
			assertEquals(entity, fooDao.lazyFind(entity.getId()));
			return entity.getId();
		});
	}

	public void assertBar(String id, String expectedBar) {
		tx(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EntityWithStringId<Foo> entity = fooDao.lazyFind(id);
				assertEquals(expectedBar, entity.getEntity().getBar());
			}
		});
	}

}
