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
package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.annotation.Nonnull;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.akiraly.db4j.TransactionTemplateAware;
import com.github.akiraly.db4j.entity.EntityWithLongId;
import com.github.akiraly.db4j.uow.FooDaoFactory.FooDao;

@Nonnull
public class FooService extends TransactionTemplateAware {
	private final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
	private final FooDao fooDao;

	public FooService(TransactionTemplate transactionTemplate, FooDao fooDao) {
		super(transactionTemplate);
		this.fooDao = argNotNull(fooDao, "fooDao");
	}

	public void addFoo(String bar, long expectedId) {
		tx(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EntityWithLongId<Foo> entity = fooDao.lazyPersist(new Foo(bar,
						now));
				assertEquals(expectedId, entity.getId());
				assertNotNull(entity.getEntity().getBar());
			}
		});
	}

	public void assertBar(long id, String expectedBar) {
		tx(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EntityWithLongId<Foo> entity = fooDao.lazyFind(id);
				assertEquals(expectedBar, entity.getEntity().getBar());
				assertEquals(now, entity.getEntity().getDt());
				assertEquals(now.toLocalDate(), entity.getEntity()
						.getLocalDate());
			}
		});
	}

}
