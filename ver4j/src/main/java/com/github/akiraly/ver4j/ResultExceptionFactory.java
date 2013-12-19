package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ResultExceptionFactory extends AExceptionFactory {
	@Override
	public RuntimeException notNullException(String name) {
		return newException("Result \"%s\" is null.", name);
	}

	@Override
	public RuntimeException notEmptyException(String name) {
		return newException("Collection result \"%s\" is empty.", name);
	}

	@Override
	protected RuntimeException createException(String message) {
		return new IllegalResultException(message);
	}
}
