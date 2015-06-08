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

import java.sql.Types;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import com.github.akiraly.db4j.JdbcTemplateAware;
import com.github.akiraly.db4j.Jsr310JdbcUtils;
import com.github.akiraly.db4j.ResultSets;
import com.github.akiraly.db4j.SimpleJdbcInsertBuilder;
import com.github.akiraly.db4j.SqlQueryBuilder;
import com.github.akiraly.db4j.SqlUpdateBuilder;
import com.github.akiraly.db4j.UuidBase64;
import com.github.akiraly.db4j.entity.EntityWithUuid;
import com.github.akiraly.db4j.entity.EntityWithUuidDao;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class FooUuidDaoFactory extends JdbcTemplateAware {
	private final SimpleJdbcInsert insert = //
	new SimpleJdbcInsertBuilder(jdbcTemplate()) //
			.tableName("foo_uuid") //
			.get();

	private final SqlUpdate deleteAll = //
	new SqlUpdateBuilder(jdbcTemplate()) //
			.sql("delete from foo_uuid") //
			.get();

	private final SqlQuery<Foo> queryById = //
	new SqlQueryBuilder<Foo>(jdbcTemplate()) //
			.sql("select * from foo_uuid where foo_id = ?") //
			.parameters(new SqlParameter("foo_id", Types.CHAR)) //
			.rowMapper((rs, rn) -> new Foo( //
					rs.getString("bar"), //
					ResultSets.readLocalDateTime(rs, "dt") //
					)) //
			.get();

	public FooUuidDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public FooUuidDao newDao() {
		return new FooUuidDao();
	}

	@Nonnull
	public class FooUuidDao extends EntityWithUuidDao<Foo> {
		@Override
		public EntityWithUuid<Foo> lazyPersist(Foo entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithUuid<Foo> lazyFind(UUID id) {
			return super.lazyFind(id);
		}

		@Override
		protected UUID persist(Foo entity) {
			final UUID id = UUID.randomUUID();
			insert.execute(ImmutableMap.of( //
					"foo_id", UuidBase64.encode(id), //
					"bar", entity.getBar(), //
					"dt", Jsr310JdbcUtils.toUtcCalendar(entity.getDt()) //
					));
			return id;
		}

		@Override
		@Nullable
		protected Foo doFind(UUID id) {
			return queryById.findObject(UuidBase64.encode(id));
		}

		public int deleteAll() {
			return deleteAll.update();
		}

		public long count() {
			return jdbcTemplate().queryForObject(
					"select count(*) from foo_uuid", Long.class);
		}
	}
}
