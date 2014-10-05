package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public class EntityWithCreateUpdateUow<T> extends EntityWithCreateUow<T> {
	private final EntityWithLongId<Uow> updateUow;

	public EntityWithCreateUpdateUow(EntityWithLongId<T> entity,
			EntityWithLongId<Uow> createUow) {
		this(entity, createUow, createUow);
	}

	public EntityWithCreateUpdateUow(EntityWithLongId<T> entity,
			EntityWithLongId<Uow> createUow, EntityWithLongId<Uow> updateUow) {
		super(createUow, entity);
		this.updateUow = argNotNull(updateUow, "updateUow");
	}

	public EntityWithLongId<Uow> getUpdateUow() {
		return updateUow;
	}
}
