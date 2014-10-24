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

import static com.github.akiraly.db4j.MoreSuppliers.memoizej8;

import java.sql.Types;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlQuery;

import com.github.akiraly.db4j.EntityWithLongId;
import com.github.akiraly.db4j.EntityWithLongIdDao;
import com.github.akiraly.db4j.JdbcTemplateAware;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class UowDaoFactory extends JdbcTemplateAware {
	private final Supplier<SimpleJdbcInsert> insert = memoizej8(() -> {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate())
				.withTableName("uow").usingGeneratedKeyColumns("uow_id");
		insert.compile();
		return insert;
	});

	private final Supplier<SqlQuery<Uow>> queryById = memoizej8(() -> {
		SqlQuery<Uow> queryById = new SqlQuery<Uow>() {
			@Override
			protected RowMapper<Uow> newRowMapper(Object[] parameters,
					Map<?, ?> context) {
				return (rs, rowNum) -> new Uow( //
						rs.getString("user") //
				);
			}
		};
		queryById.setSql("select * from uow where uow_id = ?");
		queryById.declareParameter(new SqlParameter("uow_id", Types.BIGINT));
		queryById.setJdbcTemplate(jdbcTemplate());
		queryById.afterPropertiesSet();
		return queryById;
	});

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
			return insert
					.get()
					.executeAndReturnKey(ImmutableMap.of("user", uow.getUser()))
					.longValue();
		}

		@Override
		@Nullable
		protected Uow doFind(long id) {
			return queryById.get().findObject(id);
		}

		public long count() {
			return jdbcTemplate().queryForObject("select count(*) from uow",
					Long.class);
		}
	}
}
