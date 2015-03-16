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

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.entity.EntityWithIds.EntityWithSuppliedId;
import com.github.akiraly.db4j.entity.EntityWithIds.SimpleEntityWithId;
import com.github.akiraly.db4j.entity.EntityWithIds.SuppliedEntityWithId;

@Nonnull
public abstract class EntityWithStringIds {

	private EntityWithStringIds() {
	}

	public static <E> SimpleEntityWithStringId<E> of(final E entity,
			final String id) {
		return new SimpleEntityWithStringId<>(entity, id);
	}

	public static <E> SuppliedEntityWithStringId<E> of(
			final Supplier<E> entitySupp, final String id) {
		return new SuppliedEntityWithStringId<>(entitySupp, id);
	}

	public static <E> EntityWithSuppliedStringId<E> of(final E entity,
			final Supplier<String> idSupp) {
		return new EntityWithSuppliedStringId<>(entity, idSupp);
	}

	@Nonnull
	private static class SimpleEntityWithStringId<E> extends
			SimpleEntityWithId<E, String> implements EntityWithStringId<E> {
		private SimpleEntityWithStringId(E entity, String id) {
			super(entity, id);
		}

		@Override
		public String toString() {
			return "SimpleEntityWithStringId [id=" + getId() + ", entity="
					+ getEntity() + "]";
		}
	}

	@Nonnull
	private static class SuppliedEntityWithStringId<E> extends
			SuppliedEntityWithId<E, String> implements EntityWithStringId<E> {
		private SuppliedEntityWithStringId(Supplier<E> entitySupp, String id) {
			super(entitySupp, id);
		}

		@Override
		public String toString() {
			return "SuppliedEntityWithStringId [id=" + getId()
					+ "entity=*supplier*]";
		}
	}

	@Nonnull
	private static class EntityWithSuppliedStringId<E> extends
			EntityWithSuppliedId<E, String> implements EntityWithStringId<E> {
		private EntityWithSuppliedStringId(E entity, Supplier<String> idSupp) {
			super(entity, idSupp);
		}

		@Override
		public String toString() {
			return "EntityWithSuppliedStringId [id=*supplier*, entity="
					+ getEntity() + "]";
		}
	}
}
