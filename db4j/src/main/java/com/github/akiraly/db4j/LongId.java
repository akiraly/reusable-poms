package com.github.akiraly.db4j;

import javax.annotation.Nonnull;

@Nonnull
public class LongId<T> extends SerializableId<T, Long> {
	private static final long serialVersionUID = 6376434819316965629L;

	public LongId(long id, Class<T> type) {
		super(id, type);
	}
}
