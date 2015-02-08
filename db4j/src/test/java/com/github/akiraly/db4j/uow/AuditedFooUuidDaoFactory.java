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
import static com.github.akiraly.ver4j.Verify.resultIsTrue;

import java.sql.Types;
import java.util.UUID;

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
import com.github.akiraly.db4j.UuidBase64;
import com.github.akiraly.db4j.entity.EntityWithUuid;
import com.github.akiraly.db4j.entity.EntityWithUuidDao;
import com.github.akiraly.db4j.entity.EntityWithUuids;
import com.github.akiraly.db4j.uow.UowDaoFactory.UowDao;
import com.google.common.collect.ImmutableMap;

@ParametersAreNonnullByDefault
public class AuditedFooUuidDaoFactory extends JdbcTemplateAware {

	private final SimpleJdbcInsert insert = new SimpleJdbcInsertBuilder(
			jdbcTemplate()) //
			.tableName("audited_foo_uuid").get();

	private final UUID doInsert(AuditedFoo entity) {
		argNotNull(entity, "entity");
		UUID fooId = UUID.randomUUID();
		int rowsEffected = insert.execute(ImmutableMap.of( //
				"audited_foo_uuid", UuidBase64.encode(fooId), //
				"bar", entity.getBar(), //
				"create_uow_id", entity.getCreateUow().getId(), //
				"update_uow_id", entity.getUpdateUow().getId() //
				));
		resultIsTrue(rowsEffected == 1,
				"Expected exactly 1 row effected by insert.");
		return fooId;
	}

	private final SqlUpdate update = //
	new SqlUpdateBuilder(jdbcTemplate())
			.sql("update audited_foo_uuid set bar = ?, update_uow_id = ? where audited_foo_uuid = ?")
			.addParam("bar", Types.VARCHAR)
			.addParam("update_uow_id", Types.BIGINT)
			.addParam("audited_foo_uuid", Types.VARCHAR) //
			.get();

	private final int doUpdate(EntityWithUuid<AuditedFoo> entityWithId) {
		argNotNull(entityWithId, "entityWithId");
		AuditedFoo entity = entityWithId.getEntity();
		return update.update(entity.getBar(), //
				entity.getUpdateUow().getId(), //
				UuidBase64.encode(entityWithId.getId()));
	}

	private final SqlUpdate deleteAll = //
	new SqlUpdateBuilder(jdbcTemplate()) //
			.sql("delete from audited_foo_uuid") //
			.get();

	private class QueryByIdFactory extends QueryFactory<AuditedFoo> {
		public QueryByIdFactory() {
			super(
					new SqlQueryBuilder<AuditedFoo>(jdbcTemplate()) //
							.sql("select * from audited_foo_uuid where audited_foo_uuid = ?") //
							.addParam("audited_foo_uuid", Types.VARCHAR) //
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

			AuditedFoo call(UUID id) {
				return query().findObject(UuidBase64.encode(id), context());
			}
		}
	}

	private final QueryByIdFactory queryByIdFactory = new QueryByIdFactory();

	public AuditedFooUuidDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public AuditedFooUuidDao newDao(UowDao uowDao) {
		return new AuditedFooUuidDao(uowDao);
	}

	@Nonnull
	public class AuditedFooUuidDao extends EntityWithUuidDao<AuditedFoo> {
		private final QueryByIdFactory.QueryById queryById;

		public AuditedFooUuidDao(UowDao uowDao) {
			queryById = queryByIdFactory.newQuery(uowDao);
		}

		@Override
		public EntityWithUuid<AuditedFoo> lazyPersist(AuditedFoo entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithUuid<AuditedFoo> lazyFind(UUID id) {
			return super.lazyFind(id);
		}

		@Override
		protected UUID persist(AuditedFoo entity) {
			return doInsert(entity);
		}

		@Override
		@Nullable
		protected AuditedFoo doFind(UUID id) {
			return queryById.call(id);
		}

		public EntityWithUuid<AuditedFoo> update(UUID id, AuditedFoo entity) {
			EntityWithUuid<AuditedFoo> entityWithId = EntityWithUuids.of(
					entity, id);
			update(entityWithId);
			return entityWithId;
		}

		public int update(EntityWithUuid<AuditedFoo> entityWithId) {
			return doUpdate(entityWithId);
		}

		public int deleteAll() {
			return deleteAll.update();
		}

		public long count() {
			return jdbcTemplate().queryForObject(
					"select count(*) from audited_foo_uuid", Long.class);
		}
	}
}
