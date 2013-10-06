package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

import org.junit.Test;

@Nonnull
public class VerifierTest {
	@Test(expected = IllegalArgumentException.class, timeout = 100)
	public void testArgNotNullFail() {
		Verify.argNotNull(null, "foo");
	}

	@Test(timeout = 100)
	public void testArgNotNullPass() {
		Verify.argNotNull(new Object(), "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 100)
	public void testStateNotNullFail() {
		Verify.stateNotNull(null, "foo");
	}

	@Test(timeout = 100)
	public void testStateNotNullPass() {
		Verify.stateNotNull(new Object(), "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 100)
	public void testResultNotNullFail() {
		Verify.resultNotNull(null, "foo");
	}

	@Test(timeout = 100)
	public void testResultNotNullPass() {
		Verify.resultNotNull(new Object(), "foo");
	}
}
