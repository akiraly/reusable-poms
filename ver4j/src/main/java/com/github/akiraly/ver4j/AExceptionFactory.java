package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public abstract class AExceptionFactory implements ExceptionFactory {
	@Override
	public final RuntimeException newException(String message, Object... params) {
		return newException(String.format(message, params));
	}

	@Override
	public RuntimeException newException(Object message) {
		return createException(String.valueOf(message));
	}

	protected abstract RuntimeException createException(String message);
}
