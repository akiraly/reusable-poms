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

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.sql.Types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlUpdate;

import com.github.akiraly.db4j.JdbcTemplateAware;
import com.github.akiraly.db4j.QueryFactory;
import com.github.akiraly.db4j.SimpleJdbcInsertBuilder;
import com.github.akiraly.db4j.SqlQueryBuilder;
import com.github.akiraly.db4j.SqlUpdateBuilder;
import com.github.akiraly.db4j.entity.EntityWithLongId;
import com.github.akiraly.db4j.entity.EntityWithLongIdDao;
import com.github.akiraly.db4j.uow.UowDaoFactory.UowDao;
import com.google.common.collect.ImmutableMap;

@ParametersAreNonnullByDefault
public class AuditedFooDaoFactory extends JdbcTemplateAware {

	private final SimpleJdbcInsert insert = new SimpleJdbcInsertBuilder(
			jdbcTemplate()) //
			.tableName("audited_foo") //
			.generatedKeyColumns("audited_foo_id").get();

	private final long doInsert(AuditedFoo entity) {
		argNotNull(entity, "entity");
		return insert.executeAndReturnKey(ImmutableMap.of( //
				"bar", entity.getBar(), //
				"create_uow_id", entity.getCreateUow().getId(), //
				"update_uow_id", entity.getUpdateUow().getId() //
				)).longValue();
	}

	private final SqlUpdate update = //
	new SqlUpdateBuilder(jdbcTemplate())
			.sql("update audited_foo set bar = ?, update_uow_id = ? where audited_foo_id = ?")
			.addParam("bar", Types.VARCHAR)
			.addParam("update_uow_id", Types.BIGINT)
			.addParam("audited_foo_id", Types.BIGINT) //
			.get();

	private final int doUpdate(EntityWithLongId<AuditedFoo> entityWithId) {
		argNotNull(entityWithId, "entityWithId");
		AuditedFoo entity = entityWithId.getEntity();
		return update.update(entity.getBar(), //
				entity.getUpdateUow().getId(), //
				entityWithId.getId());
	}

	private final SqlUpdate deleteAll = //
	new SqlUpdateBuilder(jdbcTemplate()) //
			.sql("delete from audited_foo") //
			.get();

	private class QueryByIdFactory extends QueryFactory<AuditedFoo> {
		public QueryByIdFactory() {
			super(new SqlQueryBuilder<AuditedFoo>(jdbcTemplate()) //
					.sql("select * from audited_foo where audited_foo_id = ?") //
					.addParam("audited_foo_id", Types.BIGINT) //
					.get());
		}

		private RowMapper<AuditedFoo> newRowMapper(UowDao uowDao) {
			return (rs, rowNum) -> new AuditedFoo( //
					rs.getString("bar"), //
					uowDao.lazyFind(rs.getLong("create_uow_id")), //
					uowDao.lazyFind(rs.getLong("update_uow_id")));
		}

		public QueryById newQuery(UowDao uowDao) {
			argNotNull(uowDao, "uowDao");
			return new QueryById(newRowMapper(uowDao));
		}

		class QueryById extends Query {
			QueryById(RowMapper<AuditedFoo> rm) {
				super(rm);
			}

			AuditedFoo call(long id) {
				return query().findObject(id, context());
			}
		}
	}

	private final QueryByIdFactory queryByIdFactory = new QueryByIdFactory();

	public AuditedFooDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public AuditedFooDao newDao(UowDao uowDao) {
		return new AuditedFooDao(uowDao);
	}

	@Nonnull
	public class AuditedFooDao extends EntityWithLongIdDao<AuditedFoo> {
		private final QueryByIdFactory.QueryById queryById;

		public AuditedFooDao(UowDao uowDao) {
			queryById = queryByIdFactory.newQuery(uowDao);
		}

		@Override
		public EntityWithLongId<AuditedFoo> lazyPersist(AuditedFoo entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithLongId<AuditedFoo> lazyFind(long id) {
			return super.lazyFind(id);
		}

		@Override
		protected long persist(AuditedFoo entity) {
			return doInsert(entity);
		}

		@Override
		@Nullable
		protected AuditedFoo doFind(long id) {
			return queryById.call(id);
		}

		public EntityWithLongId<AuditedFoo> update(long id, AuditedFoo entity) {
			EntityWithLongId<AuditedFoo> entityWithId = EntityWithLongId.of(
					entity, id);
			update(entityWithId);
			return entityWithId;
		}

		public int update(EntityWithLongId<AuditedFoo> entityWithId) {
			return doUpdate(entityWithId);
		}

		public int deleteAll() {
			return deleteAll.update();
		}

		public long count() {
			return jdbcTemplate().queryForObject(
					"select count(*) from audited_foo", Long.class);
		}
	}
}
