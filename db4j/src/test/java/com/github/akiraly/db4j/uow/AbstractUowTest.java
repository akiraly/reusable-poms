package com.github.akiraly.db4j.uow;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UowTestConfig.class })
@Nonnull
public abstract class AbstractUowTest {
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private TestCaseHandlerFactory testCaseHandlerFactory;

	protected TransactionTemplate transactionTemplate() {
		return transactionTemplate;
	}

	protected TestCaseHandlerFactory testCaseHandlerFactory() {
		return testCaseHandlerFactory;
	}

	protected static void assertPresent(Optional<?> o) {
		Assert.assertNotNull(o);
		Assert.assertTrue(o.isPresent());
	}
}