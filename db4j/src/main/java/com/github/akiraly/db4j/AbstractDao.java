package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.github.akiraly.ver4j.Verify.resultNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Optional.ofNullable;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.types.path.EntityPathBase;

@Nonnull
public abstract class AbstractDao<PK extends Serializable, E extends AbstractPersistable<PK>, Q extends EntityPathBase<E>> {
	private final DaoEntityManagerHolder<PK, E> daoEntityManagerHolder;
	private final Q path;

	protected AbstractDao(DaoEntityManagerHolder<PK, E> daoEntityManagerHolder,
			Q path) {
		this.daoEntityManagerHolder = argNotNull(daoEntityManagerHolder,
				"daoEntityManagerHolder");
		this.path = argNotNull(path, "path");
	}

	protected EntityManager entityManager() {
		return daoEntityManagerHolder.entityManager();
	}

	protected final Class<E> entityClass() {
		return daoEntityManagerHolder.entityInformation().entityClass();
	}

	protected final Class<PK> idClass() {
		return daoEntityManagerHolder.entityInformation().idClass();
	}

	protected final QueryDslJpaRepository<E, PK> repository() {
		return daoEntityManagerHolder.repository();
	}

	protected final Q path() {
		return path;
	}

	protected PK persist(E entity) {
		entityManager().persist(argNotNull(entity, "entity"));
		return resultNotNull(entity.getId(), "entity PK");
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

	protected long deleteAll() {
		return new JPADeleteClause(entityManager(), path()).execute();
	}
}
