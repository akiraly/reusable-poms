package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

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

	@Test(expected = IllegalArgumentException.class, timeout = 100)
	public void testArgCollNotNullFail() {
		Verify.argNotEmpty(null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 100)
	public void testArgCollNotNullFail2() {
		Verify.argNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 100)
	public void testArgCollNotNullPass() {
		Verify.argNotEmpty(ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 100)
	public void testStateCollNotNullFail() {
		Verify.stateNotEmpty(null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 100)
	public void testStateCollNotNullFail2() {
		Verify.stateNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 100)
	public void testStateCollNotNullPass() {
		Verify.stateNotEmpty(ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 100)
	public void testResultCollNotNullFail() {
		Verify.resultNotEmpty(null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 100)
	public void testResultCollNotNullFail2() {
		Verify.resultNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 100)
	public void testResultCollNotNullPass() {
		Verify.resultNotEmpty(ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 100)
	public void testArgIsTrueFail() {
		Verify.argIsTrue(false, "foo");
	}

	@Test(timeout = 100)
	public void testArgIsTruePass() {
		Verify.argIsTrue(true, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 100)
	public void testStateIsTrueFail() {
		Verify.stateIsTrue(false, "foo");
	}

	@Test(timeout = 100)
	public void testStateIsTruePass() {
		Verify.stateIsTrue(true, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 100)
	public void testResultIsTrueFail() {
		Verify.resultIsTrue(false, "foo");
	}

	@Test(timeout = 100)
	public void testResultIsTruePass() {
		Verify.resultIsTrue(true, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 100)
	public void testArgIsFalseFail() {
		Verify.argIsFalse(true, "foo");
	}

	@Test(timeout = 100)
	public void testArgIsFalsePass() {
		Verify.argIsFalse(false, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 100)
	public void testStateIsFalseFail() {
		Verify.stateIsFalse(true, "foo");
	}

	@Test(timeout = 100)
	public void testStateIsFalsePass() {
		Verify.stateIsFalse(false, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 100)
	public void testResultIsFalseFail() {
		Verify.resultIsFalse(true, "foo");
	}

	@Test(timeout = 100)
	public void testResultIsFalsePass() {
		Verify.resultIsFalse(false, "foo");
	}
}
