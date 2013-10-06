package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public abstract class Verify {
	private static final CompositeVerifier INSTANCE = new CompositeVerifier();

	private Verify() {
		// no-op
	}

	public static <T> T argNotNull(T object, String name) {
		return INSTANCE.argNotNull(object, name);
	}

	public static <T> T stateNotNull(T object, String name) {
		return INSTANCE.stateNotNull(object, name);
	}

	public static <T> T resultNotNull(T object, String name) {
		return INSTANCE.resultNotNull(object, name);
	}
}
