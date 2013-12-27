package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Nonnull
@MappedSuperclass
// @EntityListeners(UowUpdaterListener.class)
public abstract class AbstractCreateUpdateUowAwarePersistable<PK extends Serializable>
		extends AbstractCreateUowAwarePersistable<PK> {
	private static final long serialVersionUID = -2367224059203020147L;

	@ManyToOne(optional = false, cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "update_uow_id", nullable = false)
	private Uow updateUow;

	protected AbstractCreateUpdateUowAwarePersistable(Uow createUow) {
		super(createUow);
		this.updateUow = argNotNull(createUow, "createUow");
	}

	/**
	 * For Hibernate
	 */
	protected AbstractCreateUpdateUowAwarePersistable() {
	}

	public Uow getUpdateUow() {
		return updateUow;
	}

	public void setUpdateUow(Uow updateUow) {
		this.updateUow = updateUow;
	}
}
