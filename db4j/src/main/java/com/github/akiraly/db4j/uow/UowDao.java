package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.github.akiraly.db4j.AbstractDao;
import com.github.akiraly.db4j.EntityInformation;

@Nonnull
public class UowDao extends AbstractDao<Long, Uow> {
	public UowDao(EntityInformation<Long, Uow> entityInformation,
			QueryDslJpaRepository<Uow, Long> repository) {
		super(entityInformation, repository);
	}
}
