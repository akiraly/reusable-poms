package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Nonnull
public class Foo extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -7742225339101876655L;

	@Nullable
	private String bar;

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}
}
