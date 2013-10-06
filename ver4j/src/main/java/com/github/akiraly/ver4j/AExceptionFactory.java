package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public abstract class AExceptionFactory implements ExceptionFactory {
	protected final RuntimeException newException(String template,
			Object... params) {
		return createException(String.format(template, params));
	}

	protected abstract RuntimeException createException(String message);
}
