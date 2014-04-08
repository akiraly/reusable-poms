package com.github.akiraly.db4j.uow;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.DaoEntityManagerHolder;

@Nonnull
public class FooDao extends AbstractDao<Long, Foo, QFoo> {
	public FooDao(DaoEntityManagerHolder<Long, Foo> daoEntityManagerHolder) {
		super(daoEntityManagerHolder, QFoo.foo);
	}

	@Override
	public Long persist(Foo entity) {
		return super.persist(entity);
	}

	@Override
	public Optional<Foo> tryFind(Long key) {
		return super.tryFind(key);
	}

	@Override
	public Foo find(Long key) {
		return super.find(key);
	}

	@Override
	public long count() {
		return super.count();
	}

	public List<Foo> listAll() {
		return repository().findAll(null, path().id.desc());
	}

	@Override
	public long deleteAll() {
		return super.deleteAll();
	}
}
