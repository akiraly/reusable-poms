package com.github.akiraly.db4j.uow;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.DaoEntityManagerHolder;

@Nonnull
public class AuditedFooDao extends AbstractDao<Long, AuditedFoo, QAuditedFoo> {
	public AuditedFooDao(
			DaoEntityManagerHolder<Long, AuditedFoo> daoEntityManagerHolder) {
		super(daoEntityManagerHolder, QAuditedFoo.auditedFoo);
	}

	@Override
	public Long persist(AuditedFoo entity) {
		return super.persist(entity);
	}

	@Override
	public Optional<AuditedFoo> tryFind(Long key) {
		return super.tryFind(key);
	}

	@Override
	public AuditedFoo find(Long key) {
		return super.find(key);
	}

	@Override
	public long count() {
		return super.count();
	}
}
