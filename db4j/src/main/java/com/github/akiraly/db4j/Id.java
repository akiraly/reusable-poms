package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

@Nonnull
public abstract class Id<T> {
	private final Class<T> type;

	public Id(Class<T> type) {
		this.type = argNotNull(type, "type");
	}

	public Class<T> getType() {
		return type;
	}
}