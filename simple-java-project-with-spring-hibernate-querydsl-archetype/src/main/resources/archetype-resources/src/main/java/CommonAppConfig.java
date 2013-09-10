package $package;

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

import foo.bar.baz.entity.Foo;

@Configuration
@Nonnull
public class CommonAppConfig {
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
	public FooDaoFactory fooDaoFactory(EntityManagerFactory entityManagerFactory) {
		return new FooDaoFactory(entityManagerFactory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			DataSource dataSource, Database dbType) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(dbType);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		factory.setJpaDialect(jpaVendorAdapter.getJpaDialect());
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan(packagesToScan());
		// factory.setPersistenceUnitPostProcessors(new
		// PersistenceUnitPostProcessor() {
		// @Override
		// public void postProcessPersistenceUnitInfo(
		// MutablePersistenceUnitInfo pui) {
		// pui.addManagedClassName(Bar.class.getName());
		// pui.addManagedClassName(Foo.class.getName());
		// }
		// });
		return factory;
	}

	private String[] packagesToScan() {
		return new String[] { Foo.class.getPackage().getName() };
	}
}