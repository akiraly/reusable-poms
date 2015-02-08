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

import com.github.akiraly.db4j.entity.EntityWithLongId;

@Nonnull
public abstract class AbstractCreateUpdateUowAwarePersistable extends
		ACreateUowAware {
	private final EntityWithLongId<Uow> updateUow;

	protected AbstractCreateUpdateUowAwarePersistable(
			EntityWithLongId<Uow> createUow) {
		this(createUow, createUow);
	}

	protected AbstractCreateUpdateUowAwarePersistable(
			EntityWithLongId<Uow> createUow, EntityWithLongId<Uow> updateUow) {
		super(createUow);
		this.updateUow = argNotNull(updateUow, "updateUow");
	}

	public EntityWithLongId<Uow> getUpdateUow() {
		return updateUow;
	}
}
