package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ResultExceptionFactory extends AExceptionFactory {
	@Override
	public RuntimeException notNullException(Object name) {
		return newException("Result \"%s\" is null.", name);
	}

	@Override
	protected RuntimeException notEmptyException(String type, Object name) {
		return newException("%s result \"%s\" is empty.", type, name);
	}

	@Override
	protected RuntimeException createException(String message) {
		return new IllegalResultException(message);
	}
}
