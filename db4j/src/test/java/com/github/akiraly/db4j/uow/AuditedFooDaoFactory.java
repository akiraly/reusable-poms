package com.github.akiraly.db4j.uow;

import static com.github.akiraly.db4j.MoreSuppliers.memoizej8;
import static com.github.akiraly.ver4j.Verify.argNotNull;

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
import com.github.akiraly.db4j.uow.UowDaoFactory.UowDao;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class AuditedFooDaoFactory extends JdbcTemplateAware {
	private final Supplier<SimpleJdbcInsert> insert = memoizej8(() -> {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate())
				.withTableName("audited_foo").usingGeneratedKeyColumns(
						"audited_foo_id");
		insert.compile();
		return insert;
	});

	private final Supplier<SqlUpdate> update = memoizej8(() -> {
		SqlUpdate update = new SqlUpdate();
		update.setJdbcTemplate(jdbcTemplate());
		update.setSql("update audited_foo set bar = ?, update_uow_id = ? where audited_foo_id = ?");
		update.declareParameter(new SqlParameter("bar", Types.VARCHAR));
		update.declareParameter(new SqlParameter("update_uow_id", Types.BIGINT));
		update.declareParameter(new SqlParameter("audited_foo_id", Types.BIGINT));
		update.afterPropertiesSet();
		return update;
	});

	private final Supplier<SqlUpdate> deleteAll = memoizej8(() -> {
		SqlUpdate deleteAll = new SqlUpdate();
		deleteAll.setJdbcTemplate(jdbcTemplate());
		deleteAll.setSql("delete from audited_foo");
		deleteAll.afterPropertiesSet();
		return deleteAll;
	});

	private final Supplier<SqlQuery<AuditedFoo>> queryById = memoizej8(() -> {
		SqlQuery<AuditedFoo> queryById = new SqlQuery<AuditedFoo>() {
			@Override
			protected RowMapper<AuditedFoo> newRowMapper(Object[] parameters,
					Map<?, ?> context) {
				@SuppressWarnings("unchecked")
				ImmutableClassToInstanceMap<Object> typedContext = (ImmutableClassToInstanceMap<Object>) context;
				UowDao uowDao = typedContext.getInstance(UowDao.class);
				return (rs, rowNum) -> new AuditedFoo( //
						rs.getString("bar"), //
						uowDao.lazyFind(rs.getLong("create_uow_id")), //
						uowDao.lazyFind(rs.getLong("update_uow_id")));
			}
		};
		queryById.setSql("select * from audited_foo where audited_foo_id = ?");
		queryById.declareParameter(new SqlParameter("audited_foo_id",
				Types.BIGINT));
		queryById.setJdbcTemplate(jdbcTemplate());
		queryById.afterPropertiesSet();
		return queryById;
	});

	public AuditedFooDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public AuditedFooDao newDao(UowDao uowDao) {
		return new AuditedFooDao(uowDao);
	}

	@Nonnull
	public class AuditedFooDao extends EntityWithLongIdDao<AuditedFoo> {
		private final ImmutableClassToInstanceMap<Object> context;

		public AuditedFooDao(UowDao uowDao) {
			context = ImmutableClassToInstanceMap.builder()
					.put(UowDao.class, argNotNull(uowDao, "uowDao")).build();
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
			return insert.get().executeAndReturnKey(ImmutableMap.of( //
					"bar", entity.getBar(), //
					"create_uow_id", entity.getCreateUow().getId(), //
					"update_uow_id", entity.getUpdateUow().getId() //
					)).longValue();
		}

		@Override
		@Nullable
		protected AuditedFoo doFind(long id) {
			return queryById.get().findObject(id, context);
		}

		public EntityWithLongId<AuditedFoo> update(long id, AuditedFoo entity) {
			EntityWithLongId<AuditedFoo> entityWithId = EntityWithLongId.of(
					entity, id);
			update(entityWithId);
			return entityWithId;
		}

		public int update(EntityWithLongId<AuditedFoo> entityWithId) {
			AuditedFoo entity = entityWithId.getEntity();
			return update.get().update(entity.getBar(),
					entity.getUpdateUow().getId(), entityWithId.getId());
		}

		public int deleteAll() {
			return deleteAll.get().update();
		}

		public long count() {
			return jdbcTemplate().queryForObject(
					"select count(*) from audited_foo", Long.class);
		}
	}
}
