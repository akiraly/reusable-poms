package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.github.akiraly.ver4j.Verify.resultNotNull;
import static com.github.akiraly.ver4j.Verify.stateIsTrue;
import static com.github.akiraly.ver4j.Verify.stateNotNull;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.common.annotations.VisibleForTesting;

/**
 * Not part of the public API
 */
@Nonnull
@Deprecated
public class UowManager {
	private static final String DEFAULT_KEY = UowManager.class
			+ "_DEFAULT_TRANSACTION_KEY";
	private final Object key;

	public UowManager(Object key) {
		this.key = key;
	}

	public UowManager() {
		this(DEFAULT_KEY);
	}

	public void bind(Uow uow) {
		bind(() -> uow);
	}

	public void bind(Supplier<Uow> uow) {
		stateIsTrue(
				TransactionSynchronizationManager.isSynchronizationActive(),
				"There is no active transaction synchronization.");
		doBind(argNotNull(uow, "uow"));
		TransactionSynchronizationManager
				.registerSynchronization(new UowSynchronizer());
	}

	private void doBind(Supplier<Uow> uow) {
		TransactionSynchronizationManager.bindResource(key, uow);
	}

	@VisibleForTesting
	boolean isBound() {
		return TransactionSynchronizationManager.hasResource(key);
	}

	private Supplier<Uow> unbind() {
		stateIsTrue(isBound(), "There is no bound UOW to be unbound.");
		@SuppressWarnings("unchecked")
		Supplier<Uow> resource = (Supplier<Uow>) TransactionSynchronizationManager
				.unbindResource(key);
		return resource;
	}

	public Supplier<Uow> currentUowSupplier() {
		return currentUow(key);
	}

	public Uow currentUow() {
		return uow(currentUowSupplier());
	}

	private static Supplier<Uow> currentUow(Object key) {
		@SuppressWarnings("unchecked")
		Supplier<Uow> resource = (Supplier<Uow>) TransactionSynchronizationManager
				.getResource(key);
		stateNotNull(resource, "transaction bound UOW for " + key);
		return resource;
	}

	static Supplier<Uow> currentDefaultUowSupplier() {
		return currentUow(DEFAULT_KEY);
	}

	private static Uow uow(Supplier<Uow> uow) {
		return resultNotNull(uow.get(), "uow");
	}

	static Uow currentDefaultUow() {
		return uow(currentDefaultUowSupplier());
	}

	@Nonnull
	private class UowSynchronizer extends TransactionSynchronizationAdapter {
		private Optional<Supplier<Uow>> suspendedResource = Optional.empty();

		@Override
		public void suspend() {
			super.suspend();
			suspendedResource = Optional.of(unbind());
		}

		@Override
		public void resume() {
			super.resume();
			stateIsTrue(suspendedResource.isPresent(),
					"Uow synchronizer invoked while resuming a non-uow attached transaction.");
			doBind(suspendedResource.get());
		}

		@Override
		public void afterCompletion(int status) {
			super.afterCompletion(status);
			unbind();
		}
	}
}
