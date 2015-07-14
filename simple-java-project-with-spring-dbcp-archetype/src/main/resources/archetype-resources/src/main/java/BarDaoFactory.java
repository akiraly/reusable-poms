package ${package};

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import com.github.akiraly.db4j.JdbcTemplateAware;
import com.github.akiraly.db4j.SimpleJdbcInsertBuilder;
import com.github.akiraly.db4j.SqlQueryBuilder;
import com.github.akiraly.db4j.SqlUpdateBuilder;
import com.github.akiraly.db4j.UuidBase64;
import com.github.akiraly.db4j.entity.EntityWithStringId;
import com.github.akiraly.db4j.entity.EntityWithStringIdDao;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class BarDaoFactory extends JdbcTemplateAware {
	private final SimpleJdbcInsert insert = new SimpleJdbcInsertBuilder(
			jdbcTemplate()) //
			.tableName("bar") //
			.get();

	private final String doInsert(Bar entity) {
		argNotNull(entity, "entity");
		String id = UuidBase64.encodedRandomUUID();
		int rowsAffected = insert.execute(ImmutableMap.of( //
				"bar_uuid", id, //
				"bar_prop", entity.getBarProp() //
				));
		checkState(rowsAffected == 1, "Expected 1 inserted row, but got %s",
				rowsAffected);
		return id;
	}

	private final SqlUpdate deleteAll = //
	new SqlUpdateBuilder(jdbcTemplate()) //
			.sql("delete from bar") //
			.get();

	private final SqlQuery<Bar> queryById = //
	new SqlQueryBuilder<Bar>(jdbcTemplate()) //
			.sql("select * from bar where bar_uuid = ?") //
			.parameters(new SqlParameter("bar_uuid", Types.VARCHAR)) //
			.rowMapper((rs, rn) -> new Bar(rs.getString("bar_prop"))) //
			.get();

	public BarDaoFactory(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public BarDao newDao() {
		return new BarDao();
	}

	@Nonnull
	public class BarDao extends EntityWithStringIdDao<Bar> {
		@Override
		public EntityWithStringId<Bar> lazyPersist(Bar entity) {
			return super.lazyPersist(entity);
		}

		@Override
		public EntityWithStringId<Bar> lazyFind(String id) {
			return super.lazyFind(id);
		}

		@Override
		protected String persist(Bar entity) {
			return doInsert(entity);
		}

		@Override
		@Nullable
		protected Bar doFind(String id) {
			return queryById.findObject(id);
		}

		public int deleteAll() {
			return deleteAll.update();
		}

		public long count() {
			return jdbcTemplate().queryForObject("select count(*) from bar",
					Long.class);
		}
	}
}
