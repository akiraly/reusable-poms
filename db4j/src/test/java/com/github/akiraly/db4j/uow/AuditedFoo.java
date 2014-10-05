package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public class AuditedFoo extends AbstractCreateUpdateUowAwarePersistable {

	private final String bar;

	public AuditedFoo(String bar, EntityWithLongId<Uow> createUow) {
		super(createUow);
		this.bar = bar;
	}

	public AuditedFoo(String bar, EntityWithLongId<Uow> createUow,
			EntityWithLongId<Uow> updateUow) {
		super(createUow, updateUow);
		this.bar = bar;
	}

	public AuditedFoo updateBar(String newBar, EntityWithLongId<Uow> updateUow) {
		return new AuditedFoo(newBar, getCreateUow(), updateUow);
	}

	public String getBar() {
		return bar;
	}
}
