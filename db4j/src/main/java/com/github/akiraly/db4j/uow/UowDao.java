package com.github.akiraly.db4j.uow;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.EntityInformation;

@Nonnull
public class UowDao extends AbstractDao<Long, Uow, QUow> {
	public UowDao(Supplier<EntityManager> entityManager,
			EntityInformation<Long, Uow> entityInformation,
			Supplier<QueryDslJpaRepository<Uow, Long>> repository) {
		super(entityManager, entityInformation, repository, QUow.uow);
	}

	@Override
	public void persist(Uow entity) {
		super.persist(entity);
	}

	@Override
	public Optional<Uow> tryFind(Long key) {
		return super.tryFind(key);
	}
}
