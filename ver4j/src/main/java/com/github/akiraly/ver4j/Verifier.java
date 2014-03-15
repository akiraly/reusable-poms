package com.github.akiraly.ver4j;

import static com.google.common.collect.Iterables.isEmpty;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

@Nonnull
public class Verifier {
	private final ExceptionFactory exceptionFactory;

	public Verifier(ExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T notNull(T object, Object name) {
		if (object == null)
			throw exceptionFactory.notNullException(name);
		return object;
	}

	public <E, I extends Iterable<E>> I notEmpty(I iterable, Object name) {
		if (isEmpty(notNull(iterable, name)))
			throw exceptionFactory.notEmptyIterableException(name);
		return iterable;
	}

	public <E, C extends Collection<E>> C notEmpty(C collection, Object name) {
		if (notNull(collection, name).isEmpty())
			throw exceptionFactory.notEmptyCollectionException(name);
		return collection;
	}

	public <K, V, M extends Map<K, V>> M notEmpty(M map, Object name) {
		if (notNull(map, name).isEmpty())
			throw exceptionFactory.notEmptyMapException(name);
		return map;
	}

	public <T> T[] notEmpty(T[] array, Object name) {
		if (notNull(array, name).length < 1)
			throw exceptionFactory.notEmptyArrayException(name);
		return array;
	}

	public void isTrue(boolean check, String message, Object... params) {
		if (!check)
			throw exceptionFactory.newException(message, params);
	}

	public void isFalse(boolean check, String message, Object... params) {
		isTrue(!check, message, params);
	}
}
