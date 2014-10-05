package com.github.akiraly.db4j;

import static com.github.akiraly.db4j.MoreSuppliers.memoizej8;
import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

@Nonnull
public abstract class EntityWithLongId<T> {

	public abstract long getId();

	public abstract T getEntity();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ getId() >>> 32);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EntityWithLongId))
			return false;
		EntityWithLongId<?> other = (EntityWithLongId<?>) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}

	public static <T> EntityWithLongId<T> of(final T entity, final long id) {
		argNotNull(entity, "entity");
		return new EntityWithLongId<T>() {
			@Override
			public long getId() {
				return id;
			}

			@Override
			public T getEntity() {
				return entity;
			}

			@Override
			public String toString() {
				return "EntityWithLongId [id=" + id + ", entity=" + entity
						+ "]";
			}
		};
	}

	public static <T> EntityWithLongId<T> of(final Supplier<T> entitySupp,
			final long id) {
		argNotNull(entitySupp, "entitySupp");
		return new EntityWithLongId<T>() {
			private final Supplier<T> entity = memoizej8(entitySupp);

			@Override
			public long getId() {
				return id;
			}

			@Override
			public T getEntity() {
				return entity.get();
			}

			@Override
			public String toString() {
				return "SuppliedEntityWithLongId [id=" + id
						+ "entity=*supplier*]";
			}
		};
	}

	public static <T> EntityWithLongId<T> of(final T entity,
			final Supplier<Long> idSupp) {
		argNotNull(entity, "entity");
		argNotNull(idSupp, "idSupp");
		return new EntityWithLongId<T>() {
			private final Supplier<Long> id = memoizej8(idSupp);

			@Override
			public long getId() {
				return id.get();
			}

			@Override
			public T getEntity() {
				return entity;
			}

			@Override
			public String toString() {
				return "EntityWithSuppliedLongId [id=*supplier*, entity="
						+ entity + "]";
			}
		};
	}
}
