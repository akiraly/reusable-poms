package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class IllegalResultException extends RuntimeException {
	private static final long serialVersionUID = 2273098191220061931L;

	public IllegalResultException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalResultException(String message) {
		super(message);
	}
}
