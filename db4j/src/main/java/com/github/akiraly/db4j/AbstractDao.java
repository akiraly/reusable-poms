package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Optional.ofNullable;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

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

	protected Optional<E> tryFind(PK key) {
		return ofNullable(doFind(key));
	}

	protected E find(PK key) {
		E entity = doFind(key);
		checkState(entity != null, "Couldn't find entity with id: %s", key);
		return entity;
	}

	@Nullable
	private E doFind(PK key) {
		return entityManager().find(entityClass(), argNotNull(key, "key"));
	}

	protected long count() {
		return repository().count();
	}
}
