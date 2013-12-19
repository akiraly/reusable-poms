package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ArgExceptionFactory extends AExceptionFactory {
	@Override
	public RuntimeException notNullException(String name) {
		return newException("Argument \"%s\" is null.", name);
	}

	@Override
	public RuntimeException notEmptyException(String name) {
		return newException("Collection argument \"%s\" is null.", name);
	}

	@Override
	protected RuntimeException createException(String message) {
		return new IllegalArgumentException(message);
	}
}
