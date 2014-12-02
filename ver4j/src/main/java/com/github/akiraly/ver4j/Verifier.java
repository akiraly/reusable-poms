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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.isEmpty;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;

@Nonnull
public class Verifier {
	private final ExceptionFactory exceptionFactory;

	public Verifier(ExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T notNull(T object, Object name) {
		if (object == null)
			throw exceptionFactory.notNullException(name);
		return object;
	}

	public <E, I extends Iterator<E>> I notEmpty(I iterator, Object name) {
		if (!notNull(iterator, name).hasNext())
			throw exceptionFactory.notEmptyIteratorException(name);
		return iterator;
	}

	public <E, I extends Iterable<E>> I notEmpty(I iterable, Object name) {
		if (isEmpty(notNull(iterable, name)))
			throw exceptionFactory.notEmptyIterableException(name);
		return iterable;
	}

	public <E, C extends Collection<E>> C notEmpty(C collection, Object name) {
		if (notNull(collection, name).isEmpty())
			throw exceptionFactory.notEmptyCollectionException(name);
		return collection;
	}

	public <K, V, M extends Map<K, V>> M notEmpty(M map, Object name) {
		if (notNull(map, name).isEmpty())
			throw exceptionFactory.notEmptyMapException(name);
		return map;
	}

	public <T> T[] notEmpty(T[] array, Object name) {
		if (notNull(array, name).length < 1)
			throw exceptionFactory.notEmptyArrayException(name);
		return array;
	}

	public <C> C instanceOf(Object instance, Class<C> clazz, Object name) {
		if (!checkNotNull(clazz, "Expected not null clazz argument.")
				.isInstance(notNull(instance, name)))
			throw exceptionFactory.instanceOfException(clazz, name);
		return clazz.cast(instance);
	}

	public void isTrue(boolean check, String message, Object... params) {
		if (!check)
			throw exceptionFactory.newException(message, params);
	}

	public void isFalse(boolean check, String message, Object... params) {
		isTrue(!check, message, params);
	}
}
