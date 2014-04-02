package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Nonnull
public abstract class TransactionTemplateAware {
	private final TransactionTemplate transactionTemplate;

	protected TransactionTemplateAware(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = argNotNull(transactionTemplate,
				"transactionTemplate");
	}

	protected final <T> T tx(TransactionCallback<T> action)
			throws TransactionException {
		return transactionTemplate.execute(action);
	}

	protected final TransactionTemplate transactionTemplate() {
		return transactionTemplate;
	}
}
