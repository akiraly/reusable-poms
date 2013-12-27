package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public interface ExceptionFactory {
	RuntimeException notNullException(Object name);

	RuntimeException newException(String message, Object... params);

	RuntimeException newException(Object message);

	RuntimeException notEmptyException(Object name);
}
