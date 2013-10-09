package com.github.akiraly.uow;

import javax.annotation.Nonnull;
import javax.persistence.Entity;

import org.hibernate.annotations.Immutable;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Nonnull
@Entity
@Immutable
public class Uow extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -6140188419595214529L;

}
