package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.EntityInformation;
import com.google.common.base.Optional;

@Nonnull
public class AuditedFooDao extends AbstractDao<Long, AuditedFoo> {
	public AuditedFooDao(EntityManager entityManager,
			EntityInformation<Long, AuditedFoo> entityInformation,
			QueryDslJpaRepository<AuditedFoo, Long> repository) {
		super(entityManager, entityInformation, repository);
	}

	@Override
	public void persist(AuditedFoo entity) {
		super.persist(entity);
	}

	@Override
	public Optional<AuditedFoo> tryFind(Long key) {
		return super.tryFind(key);
	}

	@Override
	public long count() {
		return super.count();
	}
}
