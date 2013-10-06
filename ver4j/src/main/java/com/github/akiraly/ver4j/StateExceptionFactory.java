package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class StateExceptionFactory implements ExceptionFactory {

	@Override
	public RuntimeException notNullException(String name) {
		return new IllegalStateException(String.format("State \"%s\" is null.",
				name));
	}
}
