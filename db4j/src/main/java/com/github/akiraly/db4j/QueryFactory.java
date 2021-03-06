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

import static com.github.akiraly.db4j.ContextRowMapperFactory.createContext;
import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.SqlQuery;

import com.google.common.collect.ImmutableClassToInstanceMap;

/**
 * A base class for concrete queries. QueryFactor Can be singleton, creating
 * Query subclass instances per transaction.
 */
@ParametersAreNonnullByDefault
public class QueryFactory<T> {
	private final SqlQuery<T> query;

	public QueryFactory(SqlQuery<T> query) {
		this.query = argNotNull(query, "query");
	}

	protected final SqlQuery<T> query() {
		return query;
	}

	@ParametersAreNonnullByDefault
	public class Query {
		private final ImmutableClassToInstanceMap<Object> context;

		protected Query(ImmutableClassToInstanceMap<Object> context) {
			this.context = context;
		}

		protected Query(RowMapper<?> rm) {
			this(createContext(rm));
		}

		protected final ImmutableClassToInstanceMap<Object> context() {
			return context;
		}
	}
}
