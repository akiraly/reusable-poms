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
import com.github.akiraly.db4j.entity.EntityWithStringId;
import com.github.akiraly.db4j.entity.EntityWithStringIdDao;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class FooStringIdDaoFactory extends JdbcTemplateAware {
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

	public FooStringIdDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public FooStringIdDao newDao() {
		return new FooStringIdDao();
	}

	@Nonnull
	public class FooStringIdDao extends EntityWithStringIdDao<Foo> {
		@Override
		public EntityWithStringId<Foo> lazyPersist(Foo entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithStringId<Foo> lazyFind(String id) {
			return super.lazyFind(id);
		}

		@Override
		protected String persist(Foo entity) {
			final String id = UuidBase64.encodedRandomUUID();
			insert.execute(ImmutableMap.of( //
					"foo_id", id, //
					"bar", entity.getBar(), //
					"dt", Jsr310JdbcUtils.toUtcCalendar(entity.getDt()) //
					));
			return id;
		}

		@Override
		@Nullable
		protected Foo doFind(String id) {
			return queryById.findObject(id);
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
