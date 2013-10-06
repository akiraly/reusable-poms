package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ArgExceptionFactory implements ExceptionFactory {

	@Override
	public RuntimeException notNullException(String name) {
		return new IllegalArgumentException(String.format(
				"Argument \"%s\" is null.", name));
	}
}
