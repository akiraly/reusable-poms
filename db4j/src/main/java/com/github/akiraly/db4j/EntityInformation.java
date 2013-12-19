package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Nonnull
public final class EntityInformation<PK extends Serializable, E extends AbstractPersistable<PK>> {
	private final Class<PK> idClass;
	private final Class<E> entityClass;

	public EntityInformation(Class<PK> idClass, Class<E> entityClass) {
		this.idClass = argNotNull(idClass, "idClass");
		this.entityClass = argNotNull(entityClass, "entityClass");
	}

	public final Class<E> entityClass() {
		return entityClass;
	}

	public final Class<PK> idClass() {
		return idClass;
	}
}
