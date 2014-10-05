package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public class EntityWithCreateUow<T> {
	private final EntityWithLongId<Uow> createUow;

	private EntityWithLongId<T> entity;

	public EntityWithCreateUow(EntityWithLongId<Uow> createUow,
			EntityWithLongId<T> entity) {
		this.createUow = argNotNull(createUow, "createUow");
		this.entity = argNotNull(entity, "entity");
	}

	public EntityWithLongId<T> getEntity() {
		return entity;
	}

	public void setEntity(EntityWithLongId<T> entity) {
		this.entity = entity;
	}

	public EntityWithLongId<Uow> getCreateUow() {
		return createUow;
	}
}
