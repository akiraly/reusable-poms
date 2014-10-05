package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Nonnull;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.EntityWithLongId;
import com.github.akiraly.db4j.TransactionTemplateAware;
import com.github.akiraly.db4j.uow.FooDaoFactory.FooDao;

@Nonnull
public class FooService extends TransactionTemplateAware {
	private final FooDao fooDao;

	public FooService(TransactionTemplate transactionTemplate, FooDao fooDao) {
		super(transactionTemplate);
		this.fooDao = argNotNull(fooDao, "fooDao");
	}

	public void addFoo(String bar, long expectedId) {
		tx(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EntityWithLongId<Foo> entity = fooDao.lazyPersist(new Foo(bar));
				assertEquals(expectedId, entity.getId());
				assertNotNull(entity.getEntity().getBar());
			}
		});
	}

	public void assertBar(long id, String expectedBar) {
		tx(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EntityWithLongId<Foo> entity = fooDao.lazyFind(id);
				assertEquals(expectedBar, entity.getEntity().getBar());
			}
		});
	}

}
