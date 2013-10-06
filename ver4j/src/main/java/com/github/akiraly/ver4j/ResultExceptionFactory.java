package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ResultExceptionFactory implements ExceptionFactory {

	@Override
	public RuntimeException notNullException(String name) {
		return new IllegalResultException(String.format(
				"Result \"%s\" is null.", name));
	}
}
