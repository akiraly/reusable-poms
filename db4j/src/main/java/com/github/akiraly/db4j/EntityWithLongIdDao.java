package com.github.akiraly.db4j;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public abstract class EntityWithLongIdDao<E> {
	protected EntityWithLongId<E> lazyPersist(E entity) {
		return EntityWithLongId.of(entity, () -> persist(entity));
	}

	protected EntityWithLongId<E> lazyFind(long id) {
		return EntityWithLongId.of(() -> find(id), id);
	}

	protected final E find(long id) {
		return checkNotNull(doFind(id), "entity with id %s doesn't exists", id);
	}

	protected final Optional<E> tryFind(long id) {
		return Optional.ofNullable(doFind(id));
	}

	protected abstract long persist(E entity);

	@Nullable
	protected abstract E doFind(long id);
}
