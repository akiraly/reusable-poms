package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public abstract class AbstractCreateUpdateUowAwarePersistable extends
		ACreateUowAware {
	private final EntityWithLongId<Uow> updateUow;

	protected AbstractCreateUpdateUowAwarePersistable(
			EntityWithLongId<Uow> createUow) {
		this(createUow, createUow);
	}

	protected AbstractCreateUpdateUowAwarePersistable(
			EntityWithLongId<Uow> createUow, EntityWithLongId<Uow> updateUow) {
		super(createUow);
		this.updateUow = argNotNull(updateUow, "updateUow");
	}

	public EntityWithLongId<Uow> getUpdateUow() {
		return updateUow;
	}
}
