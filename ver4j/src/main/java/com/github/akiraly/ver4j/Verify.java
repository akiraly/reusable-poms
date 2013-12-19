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

	public static <E, C extends Iterable<E>> C argNotEmpty(C collection,
			String name) {
		return INSTANCE.argNotEmpty(collection, name);
	}

	public static <E, C extends Iterable<E>> C stateNotEmpty(C collection,
			String name) {
		return INSTANCE.stateNotEmpty(collection, name);
	}

	public static <E, C extends Iterable<E>> C resultNotEmpty(C collection,
			String name) {
		return INSTANCE.resultNotEmpty(collection, name);
	}

	public static void argIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.argIsTrue(check, message, params);
	}

	public static void stateIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.stateIsTrue(check, message, params);
	}

	public static void resultIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.resultIsTrue(check, message, params);
	}

	public static void argIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.argIsFalse(check, message, params);
	}

	public static void stateIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.stateIsFalse(check, message, params);
	}

	public static void resultIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.resultIsFalse(check, message, params);
	}
}
