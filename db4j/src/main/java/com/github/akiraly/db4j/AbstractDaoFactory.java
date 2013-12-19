package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.github.akiraly.ver4j.Verify.resultNotNull;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

@Nonnull
public abstract class AbstractDaoFactory<PK extends Serializable, E extends AbstractPersistable<PK>, D extends AbstractDao<PK, E>> {
	private final Class<D> daoClass;
	private final EntityInformation<PK, E> entityInformation;
	private final EntityManagerFactory entityManagerFactory;

	protected AbstractDaoFactory(Class<D> daoClass,
			EntityInformation<PK, E> entityInformation,
			EntityManagerFactory entityManagerFactory) {
		this.daoClass = argNotNull(daoClass, "daoClass");
		this.entityInformation = argNotNull(entityInformation,
				"entityInformation");
		this.entityManagerFactory = argNotNull(entityManagerFactory,
				"entityManagerFactory");
	}

	protected final EntityManager entityManagerOrFail() {
		return resultNotNull(
				EntityManagerFactoryUtils
						.getTransactionalEntityManager(entityManagerFactory),
				"thread bound entity manager");
	}

	protected final QueryDslJpaRepository<E, PK> newRepository() {
		EntityManager entityManager = entityManagerOrFail();
		JpaPersistableEntityInformation<E, PK> jpaEntityInformation = new JpaPersistableEntityInformation<E, PK>(
				entityInformation.entityClass(), entityManager.getMetamodel());
		return new QueryDslJpaRepository<>(jpaEntityInformation, entityManager);
	}

	protected final Class<D> daoClass() {
		return daoClass;
	}

	protected EntityInformation<PK, E> entityInformation() {
		return entityInformation;
	}
}
