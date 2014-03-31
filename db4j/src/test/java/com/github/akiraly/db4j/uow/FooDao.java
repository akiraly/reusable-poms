package com.github.akiraly.db4j.uow;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.EntityInformation;

@Nonnull
public class FooDao extends AbstractDao<Long, Foo, QFoo> {
	public FooDao(Supplier<EntityManager> entityManager,
			EntityInformation<Long, Foo> entityInformation,
			Supplier<QueryDslJpaRepository<Foo, Long>> repository) {
		super(entityManager, entityInformation, repository, QFoo.foo);
	}

	@Override
	public void persist(Foo entity) {
		super.persist(entity);
	}

	@Override
	public Optional<Foo> tryFind(Long key) {
		return super.tryFind(key);
	}

	@Override
	public long count() {
		return super.count();
	}

	public List<Foo> listAll() {
		return repository().findAll(null, path().id.desc());
	}
}
