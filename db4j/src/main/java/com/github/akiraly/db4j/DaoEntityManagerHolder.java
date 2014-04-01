package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.github.akiraly.ver4j.Verify.resultNotNull;
import static com.google.common.base.Suppliers.memoize;

import java.io.Serializable;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

@Nonnull
public class DaoEntityManagerHolder<PK extends Serializable, E extends AbstractPersistable<PK>> {
	private final EntityInformation<PK, E> entityInformation;
	private final Supplier<EntityManager> entityManager;
	private final Supplier<QueryDslJpaRepository<E, PK>> repository;

	public DaoEntityManagerHolder(EntityInformation<PK, E> entityInformation,
			EntityManagerFactory entityManagerFactory) {
		this.entityInformation = argNotNull(entityInformation,
				"entityInformation");
		argNotNull(entityManagerFactory, "entityManagerFactory");
		entityManager = () -> memoize(
				() -> resultNotNull(EntityManagerFactoryUtils
						.getTransactionalEntityManager(entityManagerFactory),
						"thread bound entity manager")).get();
		repository = () -> memoize(
				() -> {
					JpaPersistableEntityInformation<E, PK> jpaEntityInformation = new JpaPersistableEntityInformation<E, PK>(
							entityInformation.entityClass(), entityManager()
									.getMetamodel());
					return new QueryDslJpaRepository<>(jpaEntityInformation,
							entityManager());
				}).get();
	}

	protected EntityManager entityManager() {
		return entityManager.get();
	}

	protected final QueryDslJpaRepository<E, PK> repository() {
		return repository.get();
	}

	protected final EntityInformation<PK, E> entityInformation() {
		return entityInformation;
	}

}
