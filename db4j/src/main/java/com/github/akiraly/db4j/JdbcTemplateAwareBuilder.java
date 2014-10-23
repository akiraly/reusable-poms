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

import static com.github.akiraly.db4j.MoreSuppliers.memoizej8;
import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import org.springframework.jdbc.core.JdbcTemplate;

@Nonnull
public abstract class JdbcTemplateAwareBuilder<T, B extends JdbcTemplateAwareBuilder<T, B>>
		implements Supplier<T> {
	private JdbcTemplate jdbcTemplate;

	protected JdbcTemplateAwareBuilder() {
	}

	protected JdbcTemplateAwareBuilder(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = argNotNull(jdbcTemplate, "jdbcTemplate");
	}

	@SuppressWarnings("unchecked")
	protected final B self() {
		return (B) this;
	}

	protected final JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}

	public B jdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = argNotNull(jdbcTemplate, "jdbcTemplate");
		return self();
	}

	public final Supplier<T> lazyGet() {
		return memoizej8(() -> get());
	}
}
