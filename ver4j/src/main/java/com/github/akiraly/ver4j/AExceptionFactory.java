package com.github.akiraly.ver4j;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public abstract class AExceptionFactory implements ExceptionFactory {
	@Override
	public final RuntimeException newException(String message, Object... params) {
		for (int fi = 0; fi < params.length; fi++) {
			Object p = params[fi];
			if (p instanceof Supplier<?>)
				params[fi] = new Unwrapper(p);
		}
		return newException(String.format(message, params));
	}

	@Override
	public final RuntimeException newException(Object message) {
		return createException(String.valueOf(unwrap(message)));
	}

	@Override
	public final RuntimeException notEmptyCollectionException(Object name) {
		return notEmptyException("Collection", name);
	}

	@Override
	public RuntimeException notEmptyMapException(Object name) {
		return notEmptyException("Map", name);
	}

	@Override
	public final RuntimeException notEmptyIterableException(Object name) {
		return notEmptyException("Iterable", name);
	}

	@Override
	public final RuntimeException notEmptyArrayException(Object name) {
		return notEmptyException("Array", name);
	}

	protected abstract RuntimeException notEmptyException(String type,
			Object name);

	protected abstract RuntimeException createException(String message);

	private static Object unwrap(Object message) {
		while (message instanceof Supplier<?>)
			message = ((Supplier<?>) message).get();
		return message;
	}

	@Nullable
	private static class Unwrapper {
		private final Object raw;

		public Unwrapper(Object raw) {
			this.raw = raw;
		}

		@Override
		public String toString() {
			return String.valueOf(unwrap(raw));
		}
	}
}
