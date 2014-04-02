package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.TransactionTemplateAware;

@Nonnull
public class FooService extends TransactionTemplateAware {
	private final FooDao fooDao;

	public FooService(TransactionTemplate transactionTemplate, FooDao fooDao) {
		super(transactionTemplate);
		this.fooDao = argNotNull(fooDao, "fooDao");
	}

	public Foo addFoo(String bar) {
		Foo foo = new Foo();
		foo.setBar(bar);

		tx(s -> fooDao.persist(foo));

		return foo;
	}
}
