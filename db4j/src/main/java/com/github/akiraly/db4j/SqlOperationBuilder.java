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

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.github.akiraly.ver4j.Verify.fieldNotNull;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlOperation;

@Nonnull
public abstract class SqlOperationBuilder<T extends SqlOperation, B extends SqlOperationBuilder<T, B>>
		extends JdbcTemplateAwareBuilder<T, B> {
	private String sql;

	private SqlParameter[] parameters = new SqlParameter[0];

	protected SqlOperationBuilder() {
	}

	protected SqlOperationBuilder(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public B sql(String sql) {
		this.sql = argNotNull(sql, "sql");
		return self();
	}

	public B parameters(SqlParameter... parameters) {
		this.parameters = argNotNull(parameters, "parameters");
		return self();
	}

	@Override
	public final T get() {
		T result = newOperation();
		result.setJdbcTemplate(fieldNotNull(jdbcTemplate(), "jdbcTemplate"));
		result.setSql(fieldNotNull(sql, "sql"));
		Stream.of(parameters).forEach(param -> result.declareParameter(param));
		result.afterPropertiesSet();
		return result;
	}

	protected abstract T newOperation();
}
