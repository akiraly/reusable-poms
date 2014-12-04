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

import javax.annotation.Nonnull;

/**
 * Static methods (intended to be used as static imports) to do various runtime
 * checks: null-s, emptyness, true/false, instanceof for different scopes:
 * parameters-arguments, variables, object fields, results, states.
 * 
 * @author akiraly
 */
@Nonnull
public abstract class Verify {
	private static final CompositeVerifier INSTANCE = new CompositeVerifier();

	private Verify() {
		// no-op
	}

	public static <T> T argNotNull(T object, Object name) {
		return INSTANCE.argNotNull(object, name);
	}

	public static <T> T varNotNull(T object, Object name) {
		return INSTANCE.varNotNull(object, name);
	}

	public static <T> T fieldNotNull(T object, Object name) {
		return INSTANCE.fieldNotNull(object, name);
	}

	public static <T> T stateNotNull(T object, Object name) {
		return INSTANCE.stateNotNull(object, name);
	}

	public static <T> T resultNotNull(T object, Object name) {
		return INSTANCE.resultNotNull(object, name);
	}

	public static <E, I extends Iterator<E>> I argNotEmpty(I iterator,
			Object name) {
		return INSTANCE.argNotEmpty(iterator, name);
	}

	public static <E, I extends Iterator<E>> I varNotEmpty(I iterator,
			Object name) {
		return INSTANCE.varNotEmpty(iterator, name);
	}

	public static <E, I extends Iterator<E>> I fieldNotEmpty(I iterator,
			Object name) {
		return INSTANCE.fieldNotEmpty(iterator, name);
	}

	public static <E, I extends Iterator<E>> I stateNotEmpty(I iterator,
			Object name) {
		return INSTANCE.stateNotEmpty(iterator, name);
	}

	public static <E, I extends Iterator<E>> I resultNotEmpty(I iterator,
			Object name) {
		return INSTANCE.resultNotEmpty(iterator, name);
	}

	public static <E, I extends Iterable<E>> I argNotEmpty(I iterable,
			Object name) {
		return INSTANCE.argNotEmpty(iterable, name);
	}

	public static <E, I extends Iterable<E>> I varNotEmpty(I iterable,
			Object name) {
		return INSTANCE.varNotEmpty(iterable, name);
	}

	public static <E, I extends Iterable<E>> I fieldNotEmpty(I iterable,
			Object name) {
		return INSTANCE.fieldNotEmpty(iterable, name);
	}

	public static <E, I extends Iterable<E>> I stateNotEmpty(I iterable,
			Object name) {
		return INSTANCE.stateNotEmpty(iterable, name);
	}

	public static <E, I extends Iterable<E>> I resultNotEmpty(I iterable,
			Object name) {
		return INSTANCE.resultNotEmpty(iterable, name);
	}

	public static <E, C extends Collection<E>> C argNotEmpty(C collection,
			Object name) {
		return INSTANCE.argNotEmpty(collection, name);
	}

	public static <E, C extends Collection<E>> C varNotEmpty(C collection,
			Object name) {
		return INSTANCE.varNotEmpty(collection, name);
	}

	public static <E, C extends Collection<E>> C fieldNotEmpty(C collection,
			Object name) {
		return INSTANCE.fieldNotEmpty(collection, name);
	}

	public static <E, C extends Collection<E>> C stateNotEmpty(C collection,
			Object name) {
		return INSTANCE.stateNotEmpty(collection, name);
	}

	public static <E, C extends Collection<E>> C resultNotEmpty(C collection,
			Object name) {
		return INSTANCE.resultNotEmpty(collection, name);
	}

	public static <K, V, M extends Map<K, V>> M argNotEmpty(M map, Object name) {
		return INSTANCE.argNotEmpty(map, name);
	}

	public static <K, V, M extends Map<K, V>> M varNotEmpty(M map, Object name) {
		return INSTANCE.varNotEmpty(map, name);
	}

	public static <K, V, M extends Map<K, V>> M fieldNotEmpty(M map, Object name) {
		return INSTANCE.fieldNotEmpty(map, name);
	}

	public static <K, V, M extends Map<K, V>> M stateNotEmpty(M map, Object name) {
		return INSTANCE.stateNotEmpty(map, name);
	}

	public static <K, V, M extends Map<K, V>> M resultNotEmpty(M map,
			Object name) {
		return INSTANCE.resultNotEmpty(map, name);
	}

	public static <T> T[] argNotEmpty(T[] array, Object name) {
		return INSTANCE.argNotEmpty(array, name);
	}

	public static <T> T[] varNotEmpty(T[] array, Object name) {
		return INSTANCE.varNotEmpty(array, name);
	}

	public static <T> T[] fieldNotEmpty(T[] array, Object name) {
		return INSTANCE.fieldNotEmpty(array, name);
	}

	public static <T> T[] stateNotEmpty(T[] array, Object name) {
		return INSTANCE.stateNotEmpty(array, name);
	}

	public static <T> T[] resultNotEmpty(T[] array, Object name) {
		return INSTANCE.resultNotEmpty(array, name);
	}

	public static void argIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.argIsTrue(check, message, params);
	}

	public static void varIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.varIsTrue(check, message, params);
	}

	public static void fieldIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.fieldIsTrue(check, message, params);
	}

	public static void stateIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.stateIsTrue(check, message, params);
	}

	public static void resultIsTrue(boolean check, String message,
			Object... params) {
		INSTANCE.resultIsTrue(check, message, params);
	}

	public static void argIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.argIsFalse(check, message, params);
	}

	public static void varIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.varIsFalse(check, message, params);
	}

	public static void fieldIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.fieldIsFalse(check, message, params);
	}

	public static void stateIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.stateIsFalse(check, message, params);
	}

	public static void resultIsFalse(boolean check, String message,
			Object... params) {
		INSTANCE.resultIsFalse(check, message, params);
	}

	public static <C> C argInstanceOf(Object instance, Object name,
			Class<C> clazz) {
		return INSTANCE.argInstanceOf(instance, name, clazz);
	}

	public static <C> C varInstanceOf(Object instance, Object name,
			Class<C> clazz) {
		return INSTANCE.varInstanceOf(instance, name, clazz);
	}

	public static <C> C fieldInstanceOf(Object instance, Object name,
			Class<C> clazz) {
		return INSTANCE.fieldInstanceOf(instance, name, clazz);
	}

	public static <C> C stateInstanceOf(Object instance, Object name,
			Class<C> clazz) {
		return INSTANCE.stateInstanceOf(instance, name, clazz);
	}

	public static <C> C resultInstanceOf(Object instance, Object name,
			Class<C> clazz) {
		return INSTANCE.resultInstanceOf(instance, name, clazz);
	}
}
