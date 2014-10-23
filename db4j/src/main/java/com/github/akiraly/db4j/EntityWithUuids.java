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

import java.util.UUID;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithIds.EntityWithSuppliedId;
import com.github.akiraly.db4j.EntityWithIds.SimpleEntityWithId;
import com.github.akiraly.db4j.EntityWithIds.SuppliedEntityWithId;

@Nonnull
public abstract class EntityWithUuids {

	private EntityWithUuids() {
	}

	public static <E> SimpleEntityWithUuid<E> of(final E entity, final UUID id) {
		return new SimpleEntityWithUuid<>(entity, id);
	}

	public static <E> SuppliedEntityWithUuid<E> of(
			final Supplier<E> entitySupp, final UUID id) {
		return new SuppliedEntityWithUuid<>(entitySupp, id);
	}

	public static <E> EntityWithSuppliedUuid<E> of(final E entity,
			final Supplier<UUID> idSupp) {
		return new EntityWithSuppliedUuid<>(entity, idSupp);
	}

	@Nonnull
	public static class SimpleEntityWithUuid<E> extends
			SimpleEntityWithId<E, UUID> implements EntityWithUuid<E> {
		public SimpleEntityWithUuid(E entity, UUID id) {
			super(entity, id);
		}

		@Override
		public String toString() {
			return "SimpleEntityWithUuid [id=" + getId() + ", entity="
					+ getEntity() + "]";
		}
	}

	@Nonnull
	public static class SuppliedEntityWithUuid<E> extends
			SuppliedEntityWithId<E, UUID> implements EntityWithUuid<E> {
		public SuppliedEntityWithUuid(Supplier<E> entitySupp, UUID id) {
			super(entitySupp, id);
		}

		@Override
		public String toString() {
			return "SuppliedEntityWithUuid [id=" + getId()
					+ "entity=*supplier*]";
		}
	}

	@Nonnull
	public static class EntityWithSuppliedUuid<E> extends
			EntityWithSuppliedId<E, UUID> implements EntityWithUuid<E> {
		public EntityWithSuppliedUuid(E entity, Supplier<UUID> idSupp) {
			super(entity, idSupp);
		}

		@Override
		public String toString() {
			return "EntityWithSuppliedUuid [id=*supplier*, entity="
					+ getEntity() + "]";
		}
	}
}
