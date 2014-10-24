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

@Nonnull
public class CompositeVerifier {
	private final Verifier argV;

	private final Verifier varV;

	private final Verifier fieldV;

	private final Verifier stateV;

	private final Verifier resultV;

	public CompositeVerifier(Verifier argV, Verifier varV, Verifier fieldV,
			Verifier stateV, Verifier resultV) {
		this.argV = argV;
		this.varV = varV;
		this.fieldV = fieldV;
		this.stateV = stateV;
		this.resultV = resultV;
	}

	public CompositeVerifier() {
		this(new Verifier(new ArgExceptionFactory()), //
				new Verifier(new VarExceptionFactory()), //
				new Verifier(new FieldExceptionFactory()), //
				new Verifier(new StateExceptionFactory()), //
				new Verifier(new ResultExceptionFactory()));
	}

	public <T> T argNotNull(T object, Object name) {
		return argV.notNull(object, name);
	}

	public <T> T varNotNull(T object, Object name) {
		return varV.notNull(object, name);
	}

	public <T> T fieldNotNull(T object, Object name) {
		return fieldV.notNull(object, name);
	}

	public <T> T stateNotNull(T object, Object name) {
		return stateV.notNull(object, name);
	}

	public <T> T resultNotNull(T object, Object name) {
		return resultV.notNull(object, name);
	}

	public <E, C extends Collection<E>> C argNotEmpty(C collection, Object name) {
		return argV.notEmpty(collection, name);
	}

	public <E, C extends Collection<E>> C varNotEmpty(C collection, Object name) {
		return varV.notEmpty(collection, name);
	}

	public <E, C extends Collection<E>> C fieldNotEmpty(C collection,
			Object name) {
		return fieldV.notEmpty(collection, name);
	}

	public <E, C extends Collection<E>> C stateNotEmpty(C collection,
			Object name) {
		return stateV.notEmpty(collection, name);
	}

	public <E, C extends Collection<E>> C resultNotEmpty(C collection,
			Object name) {
		return resultV.notEmpty(collection, name);
	}

	public <K, V, M extends Map<K, V>> M argNotEmpty(M map, Object name) {
		return argV.notEmpty(map, name);
	}

	public <K, V, M extends Map<K, V>> M varNotEmpty(M map, Object name) {
		return varV.notEmpty(map, name);
	}

	public <K, V, M extends Map<K, V>> M fieldNotEmpty(M map, Object name) {
		return fieldV.notEmpty(map, name);
	}

	public <K, V, M extends Map<K, V>> M stateNotEmpty(M map, Object name) {
		return stateV.notEmpty(map, name);
	}

	public <K, V, M extends Map<K, V>> M resultNotEmpty(M map, Object name) {
		return resultV.notEmpty(map, name);
	}

	public <E, I extends Iterator<E>> I argNotEmpty(I iterator, Object name) {
		return argV.notEmpty(iterator, name);
	}

	public <E, I extends Iterator<E>> I varNotEmpty(I iterator, Object name) {
		return varV.notEmpty(iterator, name);
	}

	public <E, I extends Iterator<E>> I fieldNotEmpty(I iterator, Object name) {
		return fieldV.notEmpty(iterator, name);
	}

	public <E, I extends Iterator<E>> I stateNotEmpty(I iterator, Object name) {
		return stateV.notEmpty(iterator, name);
	}

	public <E, I extends Iterator<E>> I resultNotEmpty(I iterator, Object name) {
		return resultV.notEmpty(iterator, name);
	}

	public <E, I extends Iterable<E>> I argNotEmpty(I iterable, Object name) {
		return argV.notEmpty(iterable, name);
	}

	public <E, I extends Iterable<E>> I varNotEmpty(I iterable, Object name) {
		return varV.notEmpty(iterable, name);
	}

	public <E, I extends Iterable<E>> I fieldNotEmpty(I iterable, Object name) {
		return fieldV.notEmpty(iterable, name);
	}

	public <E, I extends Iterable<E>> I stateNotEmpty(I iterable, Object name) {
		return stateV.notEmpty(iterable, name);
	}

	public <E, I extends Iterable<E>> I resultNotEmpty(I iterable, Object name) {
		return resultV.notEmpty(iterable, name);
	}

	public <T> T[] argNotEmpty(T[] array, Object name) {
		return argV.notEmpty(array, name);
	}

	public <T> T[] varNotEmpty(T[] array, Object name) {
		return varV.notEmpty(array, name);
	}

	public <T> T[] fieldNotEmpty(T[] array, Object name) {
		return fieldV.notEmpty(array, name);
	}

	public <T> T[] stateNotEmpty(T[] array, Object name) {
		return stateV.notEmpty(array, name);
	}

	public <T> T[] resultNotEmpty(T[] array, Object name) {
		return resultV.notEmpty(array, name);
	}

	public void argIsTrue(boolean check, String message, Object... params) {
		argV.isTrue(check, message, params);
	}

	public void varIsTrue(boolean check, String message, Object... params) {
		varV.isTrue(check, message, params);
	}

	public void fieldIsTrue(boolean check, String message, Object... params) {
		fieldV.isTrue(check, message, params);
	}

	public void stateIsTrue(boolean check, String message, Object... params) {
		stateV.isTrue(check, message, params);
	}

	public void resultIsTrue(boolean check, String message, Object... params) {
		resultV.isTrue(check, message, params);
	}

	public void argIsFalse(boolean check, String message, Object... params) {
		argV.isFalse(check, message, params);
	}

	public void varIsFalse(boolean check, String message, Object... params) {
		varV.isFalse(check, message, params);
	}

	public void fieldIsFalse(boolean check, String message, Object... params) {
		fieldV.isFalse(check, message, params);
	}

	public void stateIsFalse(boolean check, String message, Object... params) {
		stateV.isFalse(check, message, params);
	}

	public void resultIsFalse(boolean check, String message, Object... params) {
		resultV.isFalse(check, message, params);
	}

}
