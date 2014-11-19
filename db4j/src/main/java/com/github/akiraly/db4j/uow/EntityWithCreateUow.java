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
package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public class EntityWithCreateUow<T> {
	private final EntityWithLongId<Uow> createUow;

	private EntityWithLongId<T> entity;

	public EntityWithCreateUow(EntityWithLongId<Uow> createUow,
			EntityWithLongId<T> entity) {
		this.createUow = argNotNull(createUow, "createUow");
		this.entity = argNotNull(entity, "entity");
	}

	public EntityWithLongId<T> getEntity() {
		return entity;
	}

	public void setEntity(EntityWithLongId<T> entity) {
		this.entity = entity;
	}

	public EntityWithLongId<Uow> getCreateUow() {
		return createUow;
	}
}
