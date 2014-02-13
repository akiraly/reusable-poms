package com.github.akiraly.ver4j;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

@Nonnull
public class VerifierTest {
	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgNotNullFail() {
		Verify.argNotNull(null, "foo");
	}

	@Test(timeout = 200)
	public void testArgNotNullPass() {
		Verify.argNotNull(new Object(), "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateNotNullFail() {
		Verify.stateNotNull(null, "foo");
	}

	@Test(timeout = 200)
	public void testStateNotNullPass() {
		Verify.stateNotNull(new Object(), "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultNotNullFail() {
		Verify.resultNotNull(null, "foo");
	}

	@Test(timeout = 200)
	public void testResultNotNullPass() {
		Verify.resultNotNull(new Object(), "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgCollNotNullFail() {
		Verify.argNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgCollNotNullFail2() {
		Verify.argNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testArgCollNotNullPass() {
		Verify.argNotEmpty(ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateCollNotNullFail() {
		Verify.stateNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateCollNotNullFail2() {
		Verify.stateNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testStateCollNotNullPass() {
		Verify.stateNotEmpty(ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultCollNotNullFail() {
		Verify.resultNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultCollNotNullFail2() {
		Verify.resultNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testResultCollNotNullPass() {
		Verify.resultNotEmpty(ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIterableNotNullFail() {
		Verify.argNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIterableNotNullFail2() {
		Verify.argNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testArgIterableNotNullPass() {
		Verify.argNotEmpty((Iterable<?>) ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIterableNotNullFail() {
		Verify.stateNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIterableNotNullFail2() {
		Verify.stateNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testStateIterableNotNullPass() {
		Verify.stateNotEmpty((Iterable<?>) ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIterableNotNullFail() {
		Verify.resultNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIterableNotNullFail2() {
		Verify.resultNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testResultIterableNotNullPass() {
		Verify.resultNotEmpty((Iterable<?>) ImmutableList.of(""), "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgArrayNotNullFail() {
		Verify.argNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgArrayNotNullFail2() {
		Verify.argNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 200)
	public void testArgArrayNotNullPass() {
		Verify.argNotEmpty(new Object[] { "" }, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateArrayNotNullFail() {
		Verify.stateNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateArrayNotNullFail2() {
		Verify.stateNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 200)
	public void testStateArrayNotNullPass() {
		Verify.stateNotEmpty(new Object[] { "" }, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultArrayNotNullFail() {
		Verify.resultNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultArrayNotNullFail2() {
		Verify.resultNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 200)
	public void testResultArrayNotNullPass() {
		Verify.resultNotEmpty(new Object[] { "" }, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIsTrueFail() {
		Verify.argIsTrue(false, "foo");
	}

	@Test(timeout = 200)
	public void testArgIsTruePass() {
		Verify.argIsTrue(true, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIsTrueFail() {
		Verify.stateIsTrue(false, "foo");
	}

	@Test(timeout = 200)
	public void testStateIsTruePass() {
		Verify.stateIsTrue(true, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIsTrueFail() {
		Verify.resultIsTrue(false, "foo");
	}

	@Test(timeout = 200)
	public void testResultIsTruePass() {
		Verify.resultIsTrue(true, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIsFalseFail() {
		Verify.argIsFalse(true, "foo");
	}

	@Test(timeout = 200)
	public void testArgIsFalsePass() {
		Verify.argIsFalse(false, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIsFalseFail() {
		Verify.stateIsFalse(true, "foo");
	}

	@Test(timeout = 200)
	public void testStateIsFalsePass() {
		Verify.stateIsFalse(false, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIsFalseFail() {
		Verify.resultIsFalse(true, "foo");
	}

	@Test(timeout = 200)
	public void testResultIsFalsePass() {
		Verify.resultIsFalse(false, "foo");
	}
}
