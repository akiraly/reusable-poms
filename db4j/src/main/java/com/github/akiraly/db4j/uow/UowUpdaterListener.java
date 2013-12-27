package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;
import javax.persistence.PreUpdate;

/**
 * Not part of the public API.
 */
@Nonnull
@Deprecated
public class UowUpdaterListener {
	@PreUpdate
	private void updateUow(AbstractCreateUpdateUowAwarePersistable<?> entity) {
		entity.setUpdateUow(UowManager.currentDefaultUow());
	}
}
