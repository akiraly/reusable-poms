package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public abstract class AExceptionFactory implements ExceptionFactory {
	@Override
	public final RuntimeException newException(String message, Object... params) {
		return newException(String.format(message, params));
	}

	@Override
	public final RuntimeException newException(Object message) {
		return createException(String.valueOf(message));
	}

	@Override
	public final RuntimeException notEmptyCollectionException(Object name) {
		return notEmptyException("Collection", name);
	}

	@Override
	public final RuntimeException notEmptyIterableException(Object name) {
		return notEmptyException("Iterable", name);
	}

	@Override
	public final RuntimeException notEmptyArrayException(Object name) {
		return notEmptyException("Array", name);
	}

	protected abstract RuntimeException notEmptyException(String type,
			Object name);

	protected abstract RuntimeException createException(String message);
}
