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

import javax.annotation.Nonnull;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Nonnull
public class SimpleJdbcInsertBuilder extends
		JdbcTemplateAwareBuilder<SimpleJdbcInsert, SimpleJdbcInsertBuilder> {
	private String tableName;

	private String[] generatedKeyColumns = new String[0];

	public SimpleJdbcInsertBuilder() {
	}

	public SimpleJdbcInsertBuilder(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public SimpleJdbcInsertBuilder tableName(String tableName) {
		this.tableName = argNotNull(tableName, "sql");
		return this;
	}

	public SimpleJdbcInsertBuilder generatedKeyColumns(
			String... generatedKeyColumns) {
		this.generatedKeyColumns = argNotNull(generatedKeyColumns,
				"generatedKeyColumns");
		return this;
	}

	@Override
	public final SimpleJdbcInsert get() {
		SimpleJdbcInsert result = new SimpleJdbcInsert(jdbcTemplate())
				.withTableName(tableName).usingGeneratedKeyColumns(
						generatedKeyColumns);
		result.compile();
		return result;
	}
}
