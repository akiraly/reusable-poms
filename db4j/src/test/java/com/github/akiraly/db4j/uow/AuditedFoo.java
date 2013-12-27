package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Entity;

@Entity
@Nonnull
public class AuditedFoo extends AbstractCreateUpdateUowAwarePersistable<Long> {

	private static final long serialVersionUID = -2556368053851608755L;

	@Nullable
	private String bar;

	protected AuditedFoo(Uow createUow) {
		super(createUow);
	}

	/**
	 * For Hibernate
	 */
	protected AuditedFoo() {
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}
}
