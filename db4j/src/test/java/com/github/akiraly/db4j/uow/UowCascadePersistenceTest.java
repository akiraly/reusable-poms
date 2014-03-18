package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.google.common.base.Optional;

@Nonnull
public class UowCascadePersistenceTest extends AbstractUowTest {
	@Test(timeout = 8000)
	public void testCascadePersistenceOnUow() {
		final String uow1User = "u200";
		final Uow uow1 = new Uow(uow1User);
		final AuditedFoo auditedFoo = new AuditedFoo(uow1);

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Assert.assertEquals(0, handler.auditedFooDao().count());
				handler.auditedFooDao().persist(auditedFoo);
				Assert.assertNotNull(auditedFoo.getId());
				Assert.assertNotNull(uow1.getId());
				Assert.assertEquals(1, handler.auditedFooDao().count());
			}
		});

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Assert.assertEquals(1, handler.auditedFooDao().count());
				Optional<AuditedFoo> fooLoaded = handler.auditedFooDao()
						.tryFind(auditedFoo.getId());
				assertPresent(fooLoaded);
				Assert.assertNotNull(fooLoaded.get().getCreateUow());
				Assert.assertSame(fooLoaded.get().getCreateUow(), fooLoaded
						.get().getUpdateUow());

				Optional<Uow> uow1Loaded = handler.uowDao().tryFind(
						uow1.getId());
				assertPresent(uow1Loaded);
				Assert.assertEquals(uow1User, uow1Loaded.get().getUser());
				Assert.assertSame(fooLoaded.get().getCreateUow(),
						uow1Loaded.get());
			}
		});

		final String uow2User = "u250";
		final Uow uow2 = new Uow(uow2User);

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Optional<AuditedFoo> fooLoaded = handler.auditedFooDao()
						.tryFind(auditedFoo.getId());
				fooLoaded.get().setBar("bar");
				fooLoaded.get().setUpdateUow(uow2);
			}
		});

		transactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				TestCaseHandler handler = testCaseHandlerFactory().get();
				Optional<AuditedFoo> fooLoaded = handler.auditedFooDao()
						.tryFind(auditedFoo.getId());
				assertPresent(fooLoaded);
				Assert.assertNotNull(fooLoaded.get().getCreateUow());
				Assert.assertNotSame(fooLoaded.get().getCreateUow(), fooLoaded
						.get().getUpdateUow());
				Assert.assertEquals(uow1User, fooLoaded.get().getCreateUow()
						.getUser());
				Assert.assertEquals(uow1, fooLoaded.get().getCreateUow());
				Assert.assertEquals(uow2User, fooLoaded.get().getUpdateUow()
						.getUser());
				Assert.assertEquals(uow2, fooLoaded.get().getUpdateUow());
			}
		});
	}
}
