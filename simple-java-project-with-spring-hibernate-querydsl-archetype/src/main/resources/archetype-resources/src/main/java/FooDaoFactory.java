package ${package};

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import foo.bar.baz.entity.Bar;
import foo.bar.baz.entity.Foo;

@Nonnull
public class FooDaoFactory {
	private final EntityManagerFactory entityManagerFactory;

	public FooDaoFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = checkNotNull(entityManagerFactory,
				"Expected not null entityManagerFactory");
	}

	public FooDao get(Bar bar) {
		EntityManager entityManager = EntityManagerFactoryUtils
				.getTransactionalEntityManager(entityManagerFactory);
		JpaPersistableEntityInformation<Foo, Long> fooPei = new JpaPersistableEntityInformation<Foo, Long>(
				Foo.class, entityManager.getMetamodel());
		QueryDslJpaRepository<Foo, Long> repository = new QueryDslJpaRepository<>(
				fooPei, entityManager);

		return new FooDao(repository, bar);
	}
}
