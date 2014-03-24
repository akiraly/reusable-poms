package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

@Nonnull
class TestCaseHandlerFactory implements Supplier<TestCaseHandler> {
	private final AuditedFooDaoFactory auditedFooDaoFactory;
	private final FooDaoFactory fooDaoFactory;
	private final UowDaoFactory uowDaoFactory;

	public TestCaseHandlerFactory(AuditedFooDaoFactory auditedFooDaoFactory,
			FooDaoFactory fooDaoFactory, UowDaoFactory uowDaoFactory) {
		this.auditedFooDaoFactory = argNotNull(auditedFooDaoFactory,
				"auditedFooDaoFactory");
		this.fooDaoFactory = argNotNull(fooDaoFactory, "fooDaoFactory");
		this.uowDaoFactory = argNotNull(uowDaoFactory, "uowDaoFactory");
	}

	@Override
	public TestCaseHandler get() {
		return new TestCaseHandler(auditedFooDaoFactory.get(),
				fooDaoFactory.get(), uowDaoFactory.get());
	}
}

@Nonnull
class TestCaseHandler {
	private final AuditedFooDao auditedFooDao;
	private final FooDao fooDao;

	private final UowDao uowDao;

	public TestCaseHandler(AuditedFooDao auditedFooDao, FooDao fooDao,
			UowDao uowDao) {
		this.auditedFooDao = argNotNull(auditedFooDao, "auditedFooDao");
		this.fooDao = argNotNull(fooDao, "fooDao");
		this.uowDao = argNotNull(uowDao, "uowDao");
	}

	AuditedFooDao auditedFooDao() {
		return auditedFooDao;
	}

	FooDao fooDao() {
		return fooDao;
	}

	UowDao uowDao() {
		return uowDao;
	}
}