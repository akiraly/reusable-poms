package com.github.akiraly.ver4j;

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

	public void isTrue(boolean check, String message, Object... params) {
		if (!check)
			throw exceptionFactory.newException(message, params);
	}

	public void isFalse(boolean check, String message, Object... params) {
		isTrue(!check, message, params);
	}
}
