/**
 * Copyright 2014 Attila Kiraly
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
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

	protected final void tx(TransactionCallbackWithoutResult action)
			throws TransactionException {
		transactionTemplate.execute(action);
	}

	protected final TransactionTemplate transactionTemplate() {
		return transactionTemplate;
	}
}
