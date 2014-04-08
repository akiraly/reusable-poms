package com.github.akiraly.db4j.uow;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.DaoEntityManagerHolder;

@Nonnull
public class UowDao extends AbstractDao<Long, Uow, QUow> {
	public UowDao(DaoEntityManagerHolder<Long, Uow> daoEntityManagerHolder) {
		super(daoEntityManagerHolder, QUow.uow);
	}

	@Override
	public Long persist(Uow entity) {
		return super.persist(entity);
	}

	@Override
	public Optional<Uow> tryFind(Long key) {
		return super.tryFind(key);
	}

	@Override
	public Uow find(Long key) {
		return super.find(key);
	}
}
