package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public interface ExceptionFactory {
	RuntimeException notNullException(String name);
}