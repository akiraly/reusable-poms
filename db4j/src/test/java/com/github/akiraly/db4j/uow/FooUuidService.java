package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.TransactionTemplateAware;
import com.github.akiraly.db4j.entity.EntityWithUuid;
import com.github.akiraly.db4j.uow.FooUuidDaoFactory.FooUuidDao;

@Nonnull
public class FooUuidService extends TransactionTemplateAware {
	private final FooUuidDao fooDao;

	public FooUuidService(TransactionTemplate transactionTemplate,
			FooUuidDao fooDao) {
		super(transactionTemplate);
		this.fooDao = argNotNull(fooDao, "fooDao");
	}

	public UUID addFoo(String bar) {
		return tx(status -> {
			EntityWithUuid<Foo> entity = fooDao.lazyPersist(new Foo(bar,
					LocalDateTime.now(ZoneOffset.UTC)));
			assertNotNull(entity.getId());
			assertNotNull(entity.getEntity().getBar());
			assertEquals(entity, fooDao.lazyFind(entity.getId()));
			return entity.getId();
		});
	}

	public void assertBar(UUID id, String expectedBar) {
		tx(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EntityWithUuid<Foo> entity = fooDao.lazyFind(id);
				assertEquals(expectedBar, entity.getEntity().getBar());
			}
		});
	}

}
