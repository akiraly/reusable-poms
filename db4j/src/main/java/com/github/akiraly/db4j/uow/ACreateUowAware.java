package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public abstract class ACreateUowAware {
	private final EntityWithLongId<Uow> createUow;

	protected ACreateUowAware(EntityWithLongId<Uow> createUow) {
		this.createUow = argNotNull(createUow, "createUow");
	}

	public EntityWithLongId<Uow> getCreateUow() {
		return createUow;
	}
}
