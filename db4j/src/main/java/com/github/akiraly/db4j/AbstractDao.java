package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.google.common.base.Optional.fromNullable;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.google.common.base.Optional;

@Nonnull
public abstract class AbstractDao<PK extends Serializable, E extends AbstractPersistable<PK>> {
	private final EntityManager entityManager;
	private final EntityInformation<PK, E> entityInformation;
	private final QueryDslJpaRepository<E, PK> repository;

	protected AbstractDao(EntityManager entityManager,
			EntityInformation<PK, E> entityInformation,
			QueryDslJpaRepository<E, PK> repository) {
		this.entityManager = argNotNull(entityManager, "entityManager");
		this.entityInformation = argNotNull(entityInformation,
				"entityInformation");
		this.repository = argNotNull(repository, "repository");
	}

	protected EntityManager entityManager() {
		return entityManager;
	}

	protected final Class<E> entityClass() {
		return entityInformation.entityClass();
	}

	protected final Class<PK> idClass() {
		return entityInformation.idClass();
	}

	protected final QueryDslJpaRepository<E, PK> repository() {
		return repository;
	}

	protected void persist(E entity) {
		entityManager().persist(argNotNull(entity, "entity"));
	}

	protected Optional<E> find(PK key) {
		return fromNullable(entityManager().find(entityClass(),
				argNotNull(key, "key")));
	}

	protected long count() {
		return repository().count();
	}
}
