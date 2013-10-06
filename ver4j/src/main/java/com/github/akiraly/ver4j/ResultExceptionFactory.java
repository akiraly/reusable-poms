package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ResultExceptionFactory extends AExceptionFactory {

	@Override
	public RuntimeException notNullException(String name) {
		return newException("Result \"%s\" is null.", name);
	}

	@Override
	public RuntimeException isTrueException(String name) {
		return newException("Result check \"%s\" is false.", name);
	}

	@Override
	public RuntimeException isFalseException(String name) {
		return newException("Result check \"%s\" is true.", name);
	}

	@Override
	protected RuntimeException createException(String message) {
		return new IllegalResultException(message);
	}
}
