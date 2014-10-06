package ${package};

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

import com.github.akiraly.db4j.CommonDbConfig;
import com.github.akiraly.db4j.pool.EmbeddedDbcpDatabaseBuilder;

@Configuration
@Import({ FooConfig.class, CommonDbConfig.class })
@Nonnull
public class FooTestConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDbcpDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(FooTest.class.getName() + "db;TRACE_LEVEL_FILE=4")
				.build();
	}

	@Bean
	public Database dbType() {
		return Database.H2;
	}
}
