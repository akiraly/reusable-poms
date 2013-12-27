package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Subclasses might be immutable.
 * 
 * @param <PK>
 *            Type of the primary key
 */
@Nonnull
@MappedSuperclass
public abstract class AbstractCreateUowAwarePersistable<PK extends Serializable>
		extends AbstractPersistable<PK> {
	private static final long serialVersionUID = -2367224059203020147L;

	@ManyToOne(optional = false, cascade = { CascadeType.PERSIST })
	private Uow createUow;

	protected AbstractCreateUowAwarePersistable(Uow createUow) {
		this.createUow = argNotNull(createUow, "createUow");
	}

	/**
	 * For Hibernate
	 */
	protected AbstractCreateUowAwarePersistable() {
	}

	public Uow getCreateUow() {
		return createUow;
	}
}
