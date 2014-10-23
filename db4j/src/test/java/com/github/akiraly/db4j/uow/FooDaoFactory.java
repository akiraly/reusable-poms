package com.github.akiraly.db4j.uow;

import java.sql.Types;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import com.github.akiraly.db4j.EntityWithLongId;
import com.github.akiraly.db4j.EntityWithLongIdDao;
import com.github.akiraly.db4j.JdbcTemplateAware;
import com.github.akiraly.db4j.SimpleJdbcInsertBuilder;
import com.github.akiraly.db4j.SqlQueryBuilder;
import com.github.akiraly.db4j.SqlUpdateBuilder;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class FooDaoFactory extends JdbcTemplateAware {
	private final Supplier<SimpleJdbcInsert> insert = //
	new SimpleJdbcInsertBuilder(jdbcTemplate()) //
			.tableName("foo") //
			.generatedKeyColumns("foo_id") //
			.lazyGet();

	private final Supplier<SqlUpdate> deleteAll = //
	new SqlUpdateBuilder(jdbcTemplate()) //
			.sql("delete from foo") //
			.lazyGet();

	private final Supplier<SqlQuery<Foo>> queryById = //
	new SqlQueryBuilder<Foo>(jdbcTemplate()) //
			.sql("select * from foo where foo_id = ?") //
			.parameters(new SqlParameter("foo_id", Types.BIGINT)) //
			.rowMapper((rs, rn) -> new Foo(rs.getString("bar"))) //
			.lazyGet();

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
