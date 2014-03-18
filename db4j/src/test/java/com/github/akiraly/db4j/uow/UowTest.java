package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.google.common.base.Optional;

@Nonnull
public class UowTest extends AbstractUowTest {
	@Test(timeout = 8000)
	public void testPersistStandaloneUow() {
		final String uow1User = "u100";
		final Uow uow1 = new Uow(uow1User);
		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				handler.uowDao().persist(uow1);
				Assert.assertNotNull(uow1.getId());
			}
		});

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Optional<Uow> uow1Loaded = handler.uowDao().tryFind(
						uow1.getId());
				assertPresent(uow1Loaded);
				Assert.assertEquals(uow1User, uow1Loaded.get().getUser());
				Assert.assertEquals(uow1, uow1Loaded.get());
			}
		});
	}
}
