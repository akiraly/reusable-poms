package com.github.akiraly.db4j.uow;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

@Nonnull
public class UowNotPersistedIfNotNeededTest extends AbstractUowTest {
	@Test(timeout = 8000)
	public void testUowNotPersistedIfNotNeeded() {
		final String uow1User = "u300";
		final Uow uow1 = new Uow(uow1User);
		final Foo foo = new Foo();

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Assert.assertEquals(0, handler.fooDao().count());
				handler.fooDao().persist(foo);
				Assert.assertNotNull(foo.getId());
				Assert.assertNull(uow1.getId());
				Assert.assertEquals(1, handler.fooDao().count());
			}
		});

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Assert.assertEquals(1, handler.fooDao().count());
				Assert.assertEquals(1, handler.fooDao().listAll().size());
				Optional<Foo> fooLoaded = handler.fooDao().tryFind(foo.getId());
				assertPresent(fooLoaded);
			}
		});
	}
}
