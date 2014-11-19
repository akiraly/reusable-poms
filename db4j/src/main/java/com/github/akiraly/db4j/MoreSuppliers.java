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
package com.github.akiraly.db4j;

import static com.google.common.base.Suppliers.memoize;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

@Nonnull
public abstract class MoreSuppliers {
	private MoreSuppliers() {
	}

	public static <T> Supplier<T> memoizej8(Supplier<T> supplier) {
		return new Supplier<T>() {
			private final com.google.common.base.Supplier<T> memoizedSupp = memoize(() -> supplier
					.get());

			@Override
			public T get() {
				return memoizedSupp.get();
			}
		};
	}
}
