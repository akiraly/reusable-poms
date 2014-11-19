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

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.EntityWithLongId;

@Nonnull
public class AuditedFoo extends AbstractCreateUpdateUowAwarePersistable {

	private final String bar;

	public AuditedFoo(String bar, EntityWithLongId<Uow> createUow) {
		super(createUow);
		this.bar = bar;
	}

	public AuditedFoo(String bar, EntityWithLongId<Uow> createUow,
			EntityWithLongId<Uow> updateUow) {
		super(createUow, updateUow);
		this.bar = bar;
	}

	public AuditedFoo updateBar(String newBar, EntityWithLongId<Uow> updateUow) {
		return new AuditedFoo(newBar, getCreateUow(), updateUow);
	}

	public String getBar() {
		return bar;
	}
}
