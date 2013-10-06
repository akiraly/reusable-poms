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

	public static void argIsTrue(boolean check, String name) {
		INSTANCE.argIsTrue(check, name);
	}

	public static void stateIsTrue(boolean check, String name) {
		INSTANCE.stateIsTrue(check, name);
	}

	public static void resultIsTrue(boolean check, String name) {
		INSTANCE.resultIsTrue(check, name);
	}

	public static void argIsFalse(boolean check, String name) {
		INSTANCE.argIsFalse(check, name);
	}

	public static void stateIsFalse(boolean check, String name) {
		INSTANCE.stateIsFalse(check, name);
	}

	public static void resultIsFalse(boolean check, String name) {
		INSTANCE.resultIsFalse(check, name);
	}
}
