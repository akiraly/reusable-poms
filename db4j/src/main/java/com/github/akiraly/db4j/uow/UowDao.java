package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.EntityInformation;
import com.google.common.base.Optional;

@Nonnull
public class UowDao extends AbstractDao<Long, Uow> {
	public UowDao(EntityManager entityManager,
			EntityInformation<Long, Uow> entityInformation,
			QueryDslJpaRepository<Uow, Long> repository) {
		super(entityManager, entityInformation, repository);
	}

	@Override
	public void persist(Uow entity) {
		super.persist(entity);
	}

	@Override
	public Optional<Uow> find(Long key) {
		return super.find(key);
	}
}