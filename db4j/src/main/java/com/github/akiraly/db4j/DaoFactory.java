package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

@Nonnull
public abstract class DaoFactory<D extends Dao> {
	private final Class<D> daoClass;

	protected DaoFactory(Class<D> daoClass) {
		this.daoClass = argNotNull(daoClass, "daoClass");
	}

	protected final Class<D> daoClass() {
		return daoClass;
	}
}
