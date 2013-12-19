package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

@Nonnull
public abstract class AbstractDao<PK extends Serializable, E extends AbstractPersistable<PK>> {
	private final EntityInformation<PK, E> entityInformation;
	private final QueryDslJpaRepository<E, PK> repository;

	protected AbstractDao(EntityInformation<PK, E> entityInformation,
			QueryDslJpaRepository<E, PK> repository) {
		this.entityInformation = argNotNull(entityInformation,
				"entityInformation");
		this.repository = argNotNull(repository, "repository");
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
}
