package com.github.akiraly.db4j;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;

@Configuration
@Nonnull
public class CommonDbConfig {
	@Bean
	public TransactionTemplate transactionTemplate(
			PlatformTransactionManager transactionManager) {
		DefaultTransactionDefinition td = new DefaultTransactionDefinition();
		td.setTimeout(30);
		return new TransactionTemplate(transactionManager, td);
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			DataSource dataSource, Database dbType,
			Collection<Set<Package>> packagesToScan) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		HibernateJpaVendorAdapter jpaVendorAdapter = jpaVendorAdapter(dbType);
		factory.setJpaDialect(jpaVendorAdapter.getJpaDialect());
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan(toStringArray(packagesToScan));
		return factory;
	}

	protected HibernateJpaVendorAdapter jpaVendorAdapter(Database dbType) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(dbType);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(showSql());
		return jpaVendorAdapter;
	}

	protected boolean showSql() {
		return false;
	}

	private String[] toStringArray(Iterable<Set<Package>> packagesToScan) {
		return ImmutableSet.copyOf(
				transform(concat(packagesToScan),
						new Function<Package, String>() {
							@Override
							public String apply(Package p) {
								return p.getName();
							}
						})).toArray(new String[0]);
	}
}