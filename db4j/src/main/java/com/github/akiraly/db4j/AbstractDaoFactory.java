package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.mysema.query.types.path.EntityPathBase;

@Nonnull
public abstract class AbstractDaoFactory<PK extends Serializable, E extends AbstractPersistable<PK>, Q extends EntityPathBase<E>, D extends AbstractDao<PK, E, Q>> {
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

	protected final DaoEntityManagerHolder<PK, E> newEntityManagerHolder() {
		return new DaoEntityManagerHolder<>(entityInformation,
				entityManagerFactory);
	}

	protected final Class<D> daoClass() {
		return daoClass;
	}

	protected EntityInformation<PK, E> entityInformation() {
		return entityInformation;
	}
}
