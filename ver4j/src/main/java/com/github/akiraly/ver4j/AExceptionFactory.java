package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public abstract class AExceptionFactory implements ExceptionFactory {
	@Override
	public final RuntimeException newException(String message, Object... params) {
		return createException(String.format(message, params));
	}

	protected abstract RuntimeException createException(String message);
}
