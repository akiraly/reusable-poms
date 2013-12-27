package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class StateExceptionFactory extends AExceptionFactory {
	@Override
	public RuntimeException notNullException(Object name) {
		return newException("State \"%s\" is null.", name);
	}

	@Override
	public RuntimeException notEmptyException(Object name) {
		return newException("Collection state \"%s\" is empty.", name);
	}

	@Override
	protected RuntimeException createException(String message) {
		return new IllegalStateException(message);
	}
}
