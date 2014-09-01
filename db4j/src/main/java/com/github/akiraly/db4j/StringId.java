package com.github.akiraly.db4j;

import javax.annotation.Nonnull;

@Nonnull
public class StringId<T> extends SerializableId<T, String> {
	private static final long serialVersionUID = 8148695307279902871L;

	public StringId(String id, Class<T> type) {
		super(id, type);
	}
}
