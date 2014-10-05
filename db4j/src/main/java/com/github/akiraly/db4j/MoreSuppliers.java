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
