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
import static com.github.akiraly.ver4j.Verify.resultNotNull;

import javax.annotation.Nonnull;

import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.ImmutableClassToInstanceMap;

/**
 * A {@link RowMapperFactory} which reads the {@link RowMapper} from the
 * supplied context during
 * {@link #newRowMapper(Object[], ImmutableClassToInstanceMap)} calls.
 * 
 * Ideal for cases when you don't have a singleton {@link RowMapper} for your
 * query.
 */
@Nonnull
public class ContextRowMapperFactory<T> implements RowMapperFactory<T> {
	private static final ContextRowMapperFactory<Object> INSTANCE = new ContextRowMapperFactory<Object>();

	@SuppressWarnings("unchecked")
	public static <T> ContextRowMapperFactory<T> get() {
		return (ContextRowMapperFactory<T>) INSTANCE;
	}

	public static <T> ImmutableClassToInstanceMap<Object> createContext(
			RowMapper<T> rowMapper) {
		return ImmutableClassToInstanceMap.builder()
				.put(RowMapper.class, argNotNull(rowMapper, "rowMapper"))
				.build();
	}

	@SuppressWarnings("unchecked")
	@Override
	public RowMapper<T> newRowMapper(Object[] parameters,
			ImmutableClassToInstanceMap<Object> context) {
		return resultNotNull(context.getInstance(RowMapper.class), "rowMapper");
	}
}
