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

import javax.annotation.Nonnull;

/**
 * Responsible for creating exceptions for a {@link Verifier} failure. The Type
 * of the created {@link RuntimeException} is decided by the factory (and not by
 * the method parameters).
 */
@Nonnull
public interface ExceptionFactory {
	/**
	 * Creates a new exception for a failed null check.
	 * 
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         null check
	 */
	RuntimeException notNullException(Object name);

	/**
	 * Creates a new exception with message templating (based on
	 * {@link String#format(String, Object...)}).
	 * 
	 * @param message
	 *            template error message
	 * @param params
	 *            to be used with the template
	 * @return a new exception with a message constructed from the passed in
	 *         message template and parameters
	 */
	RuntimeException newException(String message, Object... params);

	/**
	 * Creates a new exception with message provided.
	 * 
	 * @param message
	 *            to be used as the message of the exception
	 * @return a new exception with the provided message
	 */
	RuntimeException newException(Object message);

	/**
	 * Creates a new exception for a failed collection empty check.
	 * 
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         collection empty check
	 */
	RuntimeException notEmptyCollectionException(Object name);

	/**
	 * Creates a new exception for a failed map empty check.
	 * 
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         map empty check
	 */
	RuntimeException notEmptyMapException(Object name);

	/**
	 * Creates a new exception for a failed iterator empty check.
	 * 
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         iterator empty check
	 */
	RuntimeException notEmptyIteratorException(Object name);

	/**
	 * Creates a new exception for a failed iterable empty check.
	 * 
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         iterable empty check
	 */
	RuntimeException notEmptyIterableException(Object name);

	/**
	 * Creates a new exception for a failed array empty check.
	 * 
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         array empty check
	 */
	RuntimeException notEmptyArrayException(Object name);

	/**
	 * Creates a new exception for a failed instanceof check.
	 * 
	 * @param clazz
	 *            the type used in the check
	 * @param name
	 *            of the verification subject
	 * @return a new exception describing that the verification subject failed
	 *         instanceof check
	 */
	RuntimeException instanceOfException(Class<?> clazz, Object name);
}
