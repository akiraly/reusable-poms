/**
 * Copyright 2014 Attila Kiraly
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.akiraly.ver4j;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A Base implementation of {@link ExceptionFactory}. Supports unwrapping
 * {@link Supplier} message parameters.
 */
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
		return createException(unwrapAsString(message));
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
	public final RuntimeException notEmptyIteratorException(Object name) {
		return notEmptyException("Iterator", name);
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

	private static String unwrapAsString(Object message) {
		return String.valueOf(unwrap(message));
	}

	@Nullable
	private static class Unwrapper {
		private final Object raw;

		public Unwrapper(Object raw) {
			this.raw = raw;
		}

		@Override
		public String toString() {
			return unwrapAsString(raw);
		}
	}
}
