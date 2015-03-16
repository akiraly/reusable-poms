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

import static com.github.akiraly.db4j.MoreSuppliers.memoizej8;
import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

@Nonnull
public abstract class EntityWithIds {
	private EntityWithIds() {
	}

	public static <E, I> SimpleEntityWithId<E, I> of(final E entity, final I id) {
		return new SimpleEntityWithId<>(entity, id);
	}

	public static <E, I> SuppliedEntityWithId<E, I> of(
			final Supplier<E> entitySupp, final I id) {
		return new SuppliedEntityWithId<>(entitySupp, id);
	}

	public static <E, I> EntityWithSuppliedId<E, I> of(final E entity,
			final Supplier<I> idSupp) {
		return new EntityWithSuppliedId<>(entity, idSupp);
	}

	@Nonnull
	static class SimpleEntityWithId<E, I> extends BaseEntityWithId<E, I> {
		private final I id;
		private final E entity;

		SimpleEntityWithId(E entity, I id) {
			this.entity = argNotNull(entity, "entity");
			this.id = argNotNull(id, "id");
		}

		@Override
		public E getEntity() {
			return entity;
		}

		@Override
		public I getId() {
			return id;
		}

		@Override
		public String toString() {
			return "SimpleEntityWithId [id=" + id + ", entity=" + entity + "]";
		}
	}

	@Nonnull
	static class SuppliedEntityWithId<E, I> extends BaseEntityWithId<E, I> {
		private final I id;
		private final Supplier<E> entity;

		SuppliedEntityWithId(Supplier<E> entitySupp, I id) {
			this.entity = memoizej8(argNotNull(entitySupp, "entitySupp"));
			this.id = argNotNull(id, "id");
		}

		@Override
		public I getId() {
			return id;
		}

		@Override
		public E getEntity() {
			return entity.get();
		}

		@Override
		public String toString() {
			return "SuppliedEntityWithId [id=" + id + "entity=*supplier*]";
		}
	}

	@Nonnull
	static class EntityWithSuppliedId<E, I> extends BaseEntityWithId<E, I> {
		private final Supplier<I> id;
		private final E entity;

		EntityWithSuppliedId(E entity, Supplier<I> idSupp) {
			this.entity = argNotNull(entity, "entity");
			this.id = memoizej8(argNotNull(idSupp, "idSupp"));
		}

		@Override
		public I getId() {
			return id.get();
		}

		@Override
		public E getEntity() {
			return entity;
		}

		@Override
		public String toString() {
			return "EntityWithSuppliedId [id=*supplier*, entity=" + entity
					+ "]";
		}
	}
}
