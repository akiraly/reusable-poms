package com.github.akiraly.db4j.uow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import javax.annotation.Nonnull;

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
			final TestCaseHandler handler = testCaseHandlerFactory().get();

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				assertEquals(0, handler.fooDao().count());
				handler.fooDao().persist(foo);
				assertNotNull(foo.getId());
				assertNull(uow1.getId());
				assertEquals(1, handler.fooDao().count());
			}
		});

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				assertEquals(1, handler.fooDao().count());
				assertEquals(1, handler.fooDao().listAll().size());
				Optional<Foo> fooLoaded = handler.fooDao().tryFind(foo.getId());
				assertPresent(fooLoaded);
				assertEquals(1, handler.fooDao().deleteAll());
			}
		});
	}

	@Test(timeout = 8000)
	public void testFooService() {
		FooService fooService = new FooService(transactionTemplate(),
				testCaseHandlerFactory().get().fooDao());

		Foo foo = fooService.addFoo("bar");

		assertNotNull(foo.getId());
		assertNotNull(foo.getBar());

		assertEquals(
				1,
				transactionTemplate().execute(
						s -> testCaseHandlerFactory().get().fooDao()
								.deleteAll()).longValue());
	}
}
