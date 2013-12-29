package ${package};

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

import com.github.akiraly.db4j.CommonDbConfig;

@Configuration
@Import({ FooConfig.class, CommonDbConfig.class })
@Nonnull
public class FooTestConfig {
	@Bean
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDefaultAutoCommit(false);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setDataSource(new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(FooTestConfig.class.getName() + "db")
				.addScript("db_init.sql").build());

		return dataSource;
	}

	@Bean
	public Database dbType() {
		return Database.H2;
	}
}
