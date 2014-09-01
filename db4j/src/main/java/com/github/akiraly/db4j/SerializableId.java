package com.github.akiraly.db4j;

import java.io.Serializable;

import javax.annotation.Nonnull;

@Nonnull
public class SerializableId<T, I extends Serializable> extends SimpleId<T, I>
		implements Serializable {
	private static final long serialVersionUID = -6558836754205107372L;

	public SerializableId(I id, Class<T> type) {
		super(id, type);
	}
}
