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

import static com.github.akiraly.ver4j.Verify.argInstanceOf;
import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.SqlQuery;

import com.google.common.collect.ImmutableClassToInstanceMap;

@Nonnull
public class SqlQueryBuilder<T> extends
		SqlOperationBuilder<SqlQuery<T>, SqlQueryBuilder<T>> {
	private RowMapperFactory<T> rowMapperFactory;

	public SqlQueryBuilder() {
	}

	public SqlQueryBuilder(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public SqlQueryBuilder<T> rowMapperFactory(
			RowMapperFactory<T> rowMapperFactory) {
		this.rowMapperFactory = argNotNull(rowMapperFactory, "rowMapperFatory");
		return this;
	}

	public SqlQueryBuilder<T> rowMapper(RowMapper<T> rowMapper) {
		return rowMapperFactory(new SingletonRowMapperFactory<>(rowMapper));
	}

	@Override
	protected SqlQuery<T> newOperation() {
		return new SqlQueryWithRowMapperFactory<>(rowMapperFactory);
	}

	@Nonnull
	private static class SqlQueryWithRowMapperFactory<T> extends SqlQuery<T> {
		private final RowMapperFactory<T> rowMapperFactory;

		private SqlQueryWithRowMapperFactory(
				RowMapperFactory<T> rowMapperFactory) {
			this.rowMapperFactory = argNotNull(rowMapperFactory,
					"rowMapperFactory");
		}

		@Override
		protected RowMapper<T> newRowMapper(Object[] parameters,
				@Nullable Map<?, ?> context) {
			@SuppressWarnings("unchecked")
			@Nullable
			ImmutableClassToInstanceMap<Object> typedContext = context != null ? argInstanceOf(
					context, "context", ImmutableClassToInstanceMap.class)
					: null;
			return rowMapperFactory.newRowMapper(parameters, typedContext);
		}
	}
}
