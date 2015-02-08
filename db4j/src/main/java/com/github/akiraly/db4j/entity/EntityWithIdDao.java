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
package com.github.akiraly.db4j.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public abstract class EntityWithIdDao<E, I> {
	protected EntityWithId<E, I> lazyPersist(E entity) {
		return EntityWithIds.of(entity, () -> persist(entity));
	}

	protected EntityWithId<E, I> lazyFind(I id) {
		return EntityWithIds.of(() -> find(id), id);
	}

	protected final E find(I id) {
		return checkNotNull(doFind(id), "entity with id %s doesn't exists", id);
	}

	protected Optional<E> tryFind(I id) {
		return Optional.ofNullable(doFind(id));
	}

	protected abstract I persist(E entity);

	@Nullable
	protected abstract E doFind(I id);
}
