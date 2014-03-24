package com.github.akiraly.db4j.uow;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;

import com.github.akiraly.db4j.AbstractDaoFactory;
import com.github.akiraly.db4j.EntityInformation;

@Nonnull
public class UowDaoFactory extends AbstractDaoFactory<Long, Uow, UowDao>
		implements Supplier<UowDao> {

	public UowDaoFactory(EntityManagerFactory entityManagerFactory) {
		super(UowDao.class, new EntityInformation<>(Long.class, Uow.class),
				entityManagerFactory);
	}

	@Override
	public UowDao get() {
		return new UowDao(entityManagerOrFail(), entityInformation(),
				newRepository());
	}
}
