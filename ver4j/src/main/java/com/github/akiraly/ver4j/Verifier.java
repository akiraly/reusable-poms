package com.github.akiraly.ver4j;

import static com.google.common.collect.Iterables.isEmpty;

import javax.annotation.Nonnull;

@Nonnull
public class Verifier {
	private final ExceptionFactory exceptionFactory;

	public Verifier(ExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T notNull(T object, String name) {
		if (object == null)
			throw exceptionFactory.notNullException(name);
		return object;
	}

	public <E, C extends Iterable<E>> C notEmpty(C collection, String name) {
		if (isEmpty(notNull(collection, name)))
			throw exceptionFactory.notEmptyException(name);
		return collection;
	}

	public void isTrue(boolean check, String message, Object... params) {
		if (!check)
			throw exceptionFactory.newException(message, params);
	}

	public void isFalse(boolean check, String message, Object... params) {
		isTrue(!check, message, params);
	}
}
