package com.github.akiraly.ver4j;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

@Nonnull
public abstract class Verify {
	private static final CompositeVerifier INSTANCE = new CompositeVerifier();

	private Verify() {
		// no-op
	}

	public static <T> T argNotNull(T object, Object name) {
		return INSTANCE.argNotNull(object, name);
	}

	public static <T> T stateNotNull(T object, Object name) {
		return INSTANCE.stateNotNull(object, name);
	}

	public static <T> T resultNotNull(T object, Object name) {
		return INSTANCE.resultNotNull(object, name);
	}

	public static <E, I extends Iterable<E>> I argNotEmpty(I iterable,
			Object name) {
		return INSTANCE.argNotEmpty(iterable, name);
	}

	public static <E, I extends Iterable<E>> I stateNotEmpty(I iterable,
			Object name) {
		return INSTANCE.stateNotEmpty(iterable, name);
	}

	public static <E, I extends Iterable<E>> I resultNotEmpty(I iterable,
			Object name) {
		return INSTANCE.resultNotEmpty(iterable, name);
	}

	public static <E, C extends Collection<E>> C argNotEmpty(C collection,
			Object name) {
		return INSTANCE.argNotEmpty(collection, name);
	}

	public static <E, C extends Collection<E>> C stateNotEmpty(C collection,
			Object name) {
		return INSTANCE.stateNotEmpty(collection, name);
	}

	public static <E, C extends Collection<E>> C resultNotEmpty(C collection,
			Object name) {
		return INSTANCE.resultNotEmpty(collection, name);
	}

	public static <K, V, M extends Map<K, V>> M argNotEmpty(M map, Object name) {
		return INSTANCE.argNotEmpty(map, name);
	}

	public static <K, V, M extends Map<K, V>> M stateNotEmpty(M map, Object name) {
		return INSTANCE.stateNotEmpty(map, name);
	}

	public static <K, V, M extends Map<K, V>> M resultNotEmpty(M map,
			Object name) {
		return INSTANCE.resultNotEmpty(map, name);
	}

	public static <T> T[] argNotEmpty(T[] array, Object name) {
		return INSTANCE.argNotEmpty(array, name);
	}

	public static <T> T[] stateNotEmpty(T[] array, Object name) {
		return INSTANCE.stateNotEmpty(array, name);
	}

	public static <T> T[] resultNotEmpty(T[] array, Object name) {
		return INSTANCE.resultNotEmpty(array, name);
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
