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

import javax.annotation.Nonnull;

@Nonnull
public abstract class BaseEntityWithId<E, I> implements EntityWithId<E, I> {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		I id = getId();
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityWithId<?, ?> other = (EntityWithId<?, ?>) obj;
		I id = getId();
		Object otherId = other.getId();
		if (id == null) {
			if (otherId != null)
				return false;
		} else if (!id.equals(otherId))
			return false;
		return true;
	}
}
