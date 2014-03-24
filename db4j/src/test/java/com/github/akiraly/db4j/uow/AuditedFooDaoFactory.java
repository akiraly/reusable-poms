package com.github.akiraly.db4j.uow;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;

import com.github.akiraly.db4j.AbstractDaoFactory;
import com.github.akiraly.db4j.EntityInformation;

@Nonnull
public class AuditedFooDaoFactory extends
		AbstractDaoFactory<Long, AuditedFoo, AuditedFooDao> implements
		Supplier<AuditedFooDao> {

	public AuditedFooDaoFactory(EntityManagerFactory entityManagerFactory) {
		super(AuditedFooDao.class, new EntityInformation<>(Long.class,
				AuditedFoo.class), entityManagerFactory);
	}

	@Override
	public AuditedFooDao get() {
		return new AuditedFooDao(entityManagerOrFail(), entityInformation(),
				newRepository());
	}
}
