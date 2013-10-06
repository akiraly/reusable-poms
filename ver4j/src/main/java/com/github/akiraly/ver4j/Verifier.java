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
}
