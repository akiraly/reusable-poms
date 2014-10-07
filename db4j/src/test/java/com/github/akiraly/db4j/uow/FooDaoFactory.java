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
import org.springframework.jdbc.object.SqlUpdate;

import com.github.akiraly.db4j.EntityWithLongId;
import com.github.akiraly.db4j.EntityWithLongIdDao;
import com.github.akiraly.db4j.JdbcTemplateAware;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class FooDaoFactory extends JdbcTemplateAware {
	private final Supplier<SimpleJdbcInsert> insert = memoizej8(() -> {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate())
				.withTableName("foo").usingGeneratedKeyColumns("foo_id");
		insert.compile();
		return insert;
	});

	private final Supplier<SqlUpdate> deleteAll = memoizej8(() -> {
		SqlUpdate deleteAll = new SqlUpdate();
		deleteAll.setJdbcTemplate(jdbcTemplate());
		deleteAll.setSql("delete from foo");
		deleteAll.afterPropertiesSet();
		return deleteAll;
	});

	private final Supplier<SqlQuery<Foo>> queryById = memoizej8(() -> {
		SqlQuery<Foo> queryById = new SqlQuery<Foo>() {
			@Override
			protected RowMapper<Foo> newRowMapper(Object[] parameters,
					Map<?, ?> context) {
				return (rs, rowNum) -> new Foo( //
						rs.getString("bar") //
				);
			}
		};
		queryById.setSql("select * from foo where foo_id = ?");
		queryById.declareParameter(new SqlParameter("foo_id", Types.BIGINT));
		queryById.setJdbcTemplate(jdbcTemplate());
		queryById.afterPropertiesSet();
		return queryById;
	});

	public FooDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public FooDao newDao() {
		return new FooDao();
	}

	@Nonnull
	public class FooDao extends EntityWithLongIdDao<Foo> {
		@Override
		public EntityWithLongId<Foo> lazyPersist(Foo entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithLongId<Foo> lazyFind(long id) {
			return super.lazyFind(id);
		}

		@Override
		protected long persist(Foo entity) {
			return insert
					.get()
					.executeAndReturnKey(
							ImmutableMap.of("bar", entity.getBar()))
					.longValue();
		}

		@Override
		@Nullable
		protected Foo doFind(long id) {
			return queryById.get().findObject(id);
		}

		public int deleteAll() {
			return deleteAll.get().update();
		}

		public long count() {
			return jdbcTemplate().queryForObject("select count(*) from foo",
					Long.class);
		}
	}
}
