package com.github.akiraly.db4j.dao;

import static com.github.akiraly.db4j.Jsr310JdbcUtils.toUtcCalendar;
import static com.github.akiraly.db4j.ResultSets.readLocalDateTime;
import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.sql.Types;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlUpdate;

import com.github.akiraly.db4j.JdbcTemplateAware;
import com.github.akiraly.db4j.QueryFactory;
import com.github.akiraly.db4j.SimpleJdbcInsertBuilder;
import com.github.akiraly.db4j.SqlQueryBuilder;
import com.github.akiraly.db4j.SqlUpdateBuilder;
import com.github.akiraly.db4j.dao.BarDaoFactory.BarDao;
import com.github.akiraly.db4j.entity.EntityWithLongId;
import com.github.akiraly.db4j.entity.EntityWithLongIdDao;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class FooDaoFactory extends JdbcTemplateAware {

	private final SimpleJdbcInsert insert = new SimpleJdbcInsertBuilder(
			jdbcTemplate()) //
			.tableName("foo") //
			.generatedKeyColumns("foo_id").get();

	private final long doInsert(Foo entity) {
		argNotNull(entity, "entity");
		return insert.executeAndReturnKey(ImmutableMap.of( //
				"bar_uuid", entity.getBar().getId(), //
				"name", entity.getName(), //
				"dt", toUtcCalendar(entity.getDt()) //
				)).longValue();
	}

	private final SqlUpdate deleteAll = //
	new SqlUpdateBuilder(jdbcTemplate()) //
			.sql("delete from foo") //
			.get();

	private class QueryByIdFactory extends QueryFactory<Foo> {
		private QueryByIdFactory() {
			super(new SqlQueryBuilder<Foo>(jdbcTemplate()) //
					.sql("select * from foo where foo_id = ?") //
					.addParam("foo_id", Types.BIGINT) //
					.get());
		}

		private RowMapper<Foo> newRowMapper(BarDao barDao) {
			return (rs, rowNum) -> new Foo( //
					barDao.lazyFind(rs.getString("bar_uuid")), //
					rs.getString("name"), //
					readLocalDateTime(rs, "dt"));
		}

		public QueryById newQuery(BarDao barDao) {
			argNotNull(barDao, "barDao");
			return new QueryById(newRowMapper(barDao));
		}

		class QueryById extends Query {
			QueryById(RowMapper<Foo> rm) {
				super(rm);
			}

			Foo call(long id) {
				return query().findObject(id, context());
			}
		}
	}

	private final QueryByIdFactory queryByIdFactory = new QueryByIdFactory();

	public FooDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public FooDao newDao(BarDao barDao) {
		return new FooDao(barDao);
	}

	@Nonnull
	public class FooDao extends EntityWithLongIdDao<Foo> {
		private final QueryByIdFactory.QueryById queryById;

		public FooDao(BarDao barDao) {
			queryById = queryByIdFactory.newQuery(barDao);
		}

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
			return doInsert(entity);
		}

		@Override
		public Optional<Foo> tryFind(long id) {
			return super.tryFind(id);
		}

		@Override
		@Nullable
		protected Foo doFind(long id) {
			return queryById.call(id);
		}

		public int deleteAll() {
			return deleteAll.update();
		}

		public long count() {
			return jdbcTemplate().queryForObject("select count(*) from foo",
					Long.class);
		}
	}
}
