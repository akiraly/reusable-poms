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

import java.sql.Types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlQuery;

import com.github.akiraly.db4j.JdbcTemplateAware;
import com.github.akiraly.db4j.SimpleJdbcInsertBuilder;
import com.github.akiraly.db4j.SqlQueryBuilder;
import com.github.akiraly.db4j.entity.EntityWithLongId;
import com.github.akiraly.db4j.entity.EntityWithLongIdDao;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class UowDaoFactory extends JdbcTemplateAware {
	private final SimpleJdbcInsert insert = new SimpleJdbcInsertBuilder(
			jdbcTemplate()).tableName("uow").generatedKeyColumns("uow_id")
			.get();

	private final SqlQuery<Uow> queryById = new SqlQueryBuilder<Uow>(
			jdbcTemplate()) //
			.sql("select * from uow where uow_id = ?") //
			.parameters(new SqlParameter("uow_id", Types.BIGINT)) //
			.rowMapper((rs, rowNum) -> new Uow( //
					rs.getString("user") //
					)) //
			.get();

	public UowDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);

	}

	public UowDao newDao() {
		return new UowDao();
	}

	@Nonnull
	public class UowDao extends EntityWithLongIdDao<Uow> {
		@Override
		public EntityWithLongId<Uow> lazyPersist(Uow entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithLongId<Uow> lazyFind(long id) {
			return super.lazyFind(id);
		}

		@Override
		protected long persist(Uow uow) {
			return insert.executeAndReturnKey(
					ImmutableMap.of("user", uow.getUser())).longValue();
		}

		@Override
		@Nullable
		protected Uow doFind(long id) {
			return queryById.findObject(id);
		}

		public long count() {
			return jdbcTemplate().queryForObject("select count(*) from uow",
					Long.class);
		}
	}
}
