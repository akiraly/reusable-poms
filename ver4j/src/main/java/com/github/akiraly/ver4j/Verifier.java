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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

/**
 * Methods in this class an be used for various checks. If a check fails a
 * {@link RuntimeException} is thrown (created by the {@link ExceptionFactory}
 * provided in the constructor).
 * 
 * In every case where an Object name is expected as a parameter in case of
 * errors:<br />
 * - if the name Object is instanceof {@link Supplier} then
 * {@link Supplier#get()} is called<br />
 * - {@link Object#toString()} is called to get a String for the exception
 * message
 */
@Nonnull
public class Verifier {
	private final ExceptionFactory exceptionFactory;

	public Verifier(ExceptionFactory exceptionFactory) {
		this.exceptionFactory = Objects.requireNonNull(exceptionFactory);
	}

	/**
	 * Checks that the provided object is not null
	 * 
	 * @param object
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @return the checked object in case it passed the check
	 */
	public <T> T notNull(T object, Object name) {
		if (object == null)
			throw exceptionFactory.notNullException(name);
		return object;
	}

	/**
	 * Checks that the provided iterator is not null and not empty
	 * 
	 * @param iterator
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @return the checked object in case it passed the check
	 */
	public <E, I extends Iterator<E>> I notEmpty(I iterator, Object name) {
		if (!notNull(iterator, name).hasNext())
			throw exceptionFactory.notEmptyIteratorException(name);
		return iterator;
	}

	/**
	 * Checks that the provided iterable is not null and not empty
	 * 
	 * @param iterable
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @return the checked object in case it passed the check
	 */
	public <E, I extends Iterable<E>> I notEmpty(I iterable, Object name) {
		if (iterable instanceof Collection) {
			@SuppressWarnings("unchecked")
			final I notEmptyCollection = (I) notEmpty((Collection<E>) iterable,
					name);
			return notEmptyCollection;
		}
		Iterator<E> iterator = notNull(iterable, name).iterator();
		Supplier<String> message = () -> name + ".iterator()";
		if (!notNull(iterator, message).hasNext())
			throw exceptionFactory.notEmptyIterableException(name);
		return iterable;
	}

	/**
	 * Checks that the provided collection is not null and not empty
	 * 
	 * @param collection
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @return the checked object in case it passed the check
	 */
	public <E, C extends Collection<E>> C notEmpty(C collection, Object name) {
		if (notNull(collection, name).isEmpty())
			throw exceptionFactory.notEmptyCollectionException(name);
		return collection;
	}

	/**
	 * Checks that the provided map is not null and not empty
	 * 
	 * @param map
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @return the checked object in case it passed the check
	 */
	public <K, V, M extends Map<K, V>> M notEmpty(M map, Object name) {
		if (notNull(map, name).isEmpty())
			throw exceptionFactory.notEmptyMapException(name);
		return map;
	}

	/**
	 * Checks that the provided array is not null and not empty
	 * 
	 * @param array
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @return the checked object in case it passed the check
	 */
	public <T> T[] notEmpty(T[] array, Object name) {
		if (notNull(array, name).length < 1)
			throw exceptionFactory.notEmptyArrayException(name);
		return array;
	}

	/**
	 * Checks that the provided object is not null and instace of the provided
	 * type.
	 * 
	 * @param instance
	 *            to be checked
	 * @param name
	 *            name of the object for error message
	 * @param clazz
	 *            the type to be used for the instanceof check
	 * @return the checked object in case it passed the check, casted to the
	 *         checked type
	 */
	public <C> C instanceOf(Object instance, Object name, Class<C> clazz) {
		if (!Objects.requireNonNull(clazz, "Expected not null clazz argument.")
				.isInstance(notNull(instance, name)))
			throw exceptionFactory.instanceOfException(clazz, name);
		return clazz.cast(instance);
	}

	/**
	 * Checks that the provided boolean is true.
	 * 
	 * @param check
	 *            to be checked
	 * @param message
	 *            error message template
	 * @param params
	 *            error message template parameters
	 */
	public void isTrue(boolean check, String message, Object... params) {
		if (!check)
			throw exceptionFactory.newException(message, params);
	}

	/**
	 * Checks that the provided boolean is false.
	 * 
	 * @param check
	 *            to be checked
	 * @param message
	 *            error message template
	 * @param params
	 *            error message template parameters
	 */
	public void isFalse(boolean check, String message, Object... params) {
		isTrue(!check, message, params);
	}
}
