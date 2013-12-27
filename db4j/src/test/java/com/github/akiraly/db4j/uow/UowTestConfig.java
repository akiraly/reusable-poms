package com.github.akiraly.db4j.uow;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

import com.github.akiraly.db4j.CommonDbConfig;
import com.google.common.collect.ImmutableSet;

@Configuration
@Import({ UowConfig.class })
@Nonnull
class UowTestConfig extends CommonDbConfig {
	@Bean
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDefaultAutoCommit(false);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setDataSource(new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName(UowTest.class.getName() + "db").build());

		return dataSource;
	}

	@Bean
	public Database dbType() {
		return Database.H2;
	}

	@Override
	protected boolean showSql() {
		return true;
	}

	@Bean
	public Set<Package> packagesToScan() {
		return ImmutableSet.of(AuditedFoo.class.getPackage());
	}

	@Bean
	public AuditedFooDaoFactory auditedFooDaoFactory(
			EntityManagerFactory entityManagerFactory) {
		return new AuditedFooDaoFactory(entityManagerFactory);
	}

	@Bean
	public FooDaoFactory fooDaoFactory(EntityManagerFactory entityManagerFactory) {
		return new FooDaoFactory(entityManagerFactory);
	}

	@Bean
	public TestCaseHandlerFactory testCaseHandlerFactory(
			UowDaoFactory uowDaoFactory,
			AuditedFooDaoFactory auditedFooDaoFactory,
			FooDaoFactory fooDaoFactory) {
		return new TestCaseHandlerFactory(auditedFooDaoFactory, fooDaoFactory,
				uowDaoFactory);
	}
}